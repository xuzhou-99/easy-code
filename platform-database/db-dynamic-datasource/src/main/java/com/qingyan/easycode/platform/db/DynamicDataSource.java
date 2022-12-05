package com.qingyan.easycode.platform.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.qingyan.easycode.platform.core.exception.BizException;
import com.qingyan.easycode.platform.db.constans.DataSourceTypeEnum;
import com.qingyan.easycode.platform.db.constans.DynamicDataSourceStatusEnum;
import com.qingyan.easycode.platform.db.entity.TenantDataSources;
import com.qingyan.easycode.platform.db.tenant.MasterTenantHandler;
import com.qingyan.easycode.platform.tenant.constans.TenantConstants;
import com.qingyan.easycode.platform.tenant.entity.TenantInfo;
import com.qingyan.easycode.platform.tenant.exception.TenantNotExistException;


/**
 * 动态数据源实现类
 *
 * @author xuzhou
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);
    /**
     * 存储已经注册的数据源的key-value
     */
    private static final Map<String, TenantDataSources> TENANT_DATA_SOURCES_MAP = new ConcurrentHashMap<>();
    /**
     * 用于存放当前线程的企业信息 适用于没有request对象的后台程序切换数据源
     */
    private static final ThreadLocal<Long> CURRENT_TENANT_ID = new ThreadLocal<>();


    /**
     * 添加一个企业数据源到缓存中
     *
     * @param tenantId          企业ID
     * @param tenantDataSources 企业数据源
     */
    public static void registerTenantDataSources(Long tenantId, TenantDataSources tenantDataSources) {

        log.info("register tenant dataSources to dynamicDataSource success, [tenantId]:{}", tenantId);

        Assert.notNull(tenantId, "register tenant dataSources need tenantId not null");
        Assert.notNull(tenantDataSources, "register tenant dataSources need tenantDataSources not null");

        TENANT_DATA_SOURCES_MAP.put(tenantId.toString(), tenantDataSources);
    }

    /**
     * 从缓存中移除数据源(对于已注销的企业)，主库从库一起注销
     *
     * @param tenantId 企业Id
     */
    public static void removeDataSource(Long tenantId) {

        if (null == tenantId) {
            return;
        }

        log.info("DynamicDatasource receive command to remove dataSource,[tenantId]:{}", tenantId);

        //从缓存map删除
        TenantDataSources tenantDataSources = TENANT_DATA_SOURCES_MAP.remove(tenantId.toString());

        //删除 的缓存key，创建jdbc连接时生成的缓存
        MasterTenantHandler.deleteDbInfoCache(tenantId);

        //摧毁连接池
        if (tenantDataSources != null) {
            tenantDataSources.removeAndDestroy();
        }

        log.info("DynamicDatasource success remove dataSource,[tenantId]:{}", tenantId);
    }

    /**
     * 获取企业的数据源
     *
     * @param tenantId 企业Id
     * @return 企业数据源
     */
    public static TenantDataSources getTenantDataSources(Long tenantId) {
        return TENANT_DATA_SOURCES_MAP.get(tenantId.toString());
    }

    /**
     * 检查企业的数据源是否已创建在缓存中，主库和从库一起检查
     *
     * @param tenantId 企业Id
     * @return 是否已有数据源缓存
     */
    public static boolean isDataSourceExist(Long tenantId) {
        return TENANT_DATA_SOURCES_MAP.containsKey(tenantId.toString());
    }

    /**
     * 获取当前线程的数据源key
     *
     * @return Long
     */
    public static Long getCurTenant() {
        return CURRENT_TENANT_ID.get();
    }

    /**
     * 设置当前的企业的ID （适用于没有request对象的后台程序切换数据源）
     *
     * @param tenantId 企业Id
     */
    public static void setCurTenant(Long tenantId) {
        if (tenantId != null && tenantId > 0) {
            log.info("setCurTenant datasource ThreadLocal:{}", tenantId);
            CURRENT_TENANT_ID.set(tenantId);
        }
    }

    /**
     * 清除当前线程中的TenantId
     */
    public static void clearCurTenant() {
        Long tenantId = CURRENT_TENANT_ID.get();
        if (tenantId != null && tenantId > 0) {
            CURRENT_TENANT_ID.remove();
        }

        // 清楚tag
        clearOneLevelDataBaseType();
    }

    /**
     * 清除数据源类型
     */
    public static void clearOneLevelDataBaseType() {
        DatabaseRouter.removeOne();
    }

    /**
     * 尝试从各种渠道获取 tenantId
     *
     * @return tenantId
     */
    public static Long tryFetchTenantId() {

        Long tenantId = CURRENT_TENANT_ID.get();
        if (tenantId != null) {
            return tenantId;
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            // 获取 HttpServletRequest
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            if (request != null) {
                HttpSession session = request.getSession(false);
                if (session != null && null != session.getAttribute(TenantConstants.TENANT_ID)) {
                    tenantId = (Long) session.getAttribute(TenantConstants.TENANT_ID);
                }
            }
        }

        //如果session里的有效，设置到 ThreadLocal
        if (tenantId != null && tenantId > 0) {
            setCurTenant(tenantId);
        } else {
            log.info("从请求中无法获得当前企业ID！");
        }

        return tenantId;
    }

    /**
     * 切换企业数据源
     *
     * @param tenantInfo 企业信息
     * @return 数据源切换状态
     */
    public static int switchConnection(TenantInfo tenantInfo) {
        if (tenantInfo == null) {
            log.info("数据源连接切换失败，企业信息为空！");
            return DynamicDataSourceStatusEnum.DATASOURCE_TENANT_NULL.getCode();
        }

        try {
            if (RegisterTenantDatasource.getTenantDatasource(tenantInfo)) {
                return DynamicDataSourceStatusEnum.DATASOURCE_SUCCESS.getCode();
            }
            if (TenantConstants.STATUS_STOP.equals(tenantInfo.getStatus())
                    || TenantConstants.STATUS_FORBIDDEN.equals(tenantInfo.getStatus())
                    || TenantConstants.STATUS_CANCELLATION.equals(tenantInfo.getStatus())) {
                log.info("企业[{}]数据源连接切换失败，已经销户或停用！", tenantInfo.getName());
                return DynamicDataSourceStatusEnum.DATASOURCE_TENANT_DEACTIVATED.getCode();
            }
        } catch (Exception e) {
            log.warn("企业[{}]数据源连接切换失败，原因：{}", tenantInfo.getName(), e.getMessage());
            return DynamicDataSourceStatusEnum.DATASOURCE_SEVER_ERROR.getCode();
        }
        return DynamicDataSourceStatusEnum.DATASOURCE_FAIL.getCode();
    }

    /**
     * 切换数据源连接
     *
     * @param tenantId 企业Id
     * @return 数据源切换状态
     */
    public static int switchConnection(Long tenantId) {
        if (tenantId == null || tenantId < 0) {
            log.info("数据源连接切换失败，企业信息为空！");
            return DynamicDataSourceStatusEnum.DATASOURCE_TENANT_NULL.getCode();
        }

        TenantInfo tenantInfo = MasterTenantHandler.getTenantInfo(tenantId);
        return switchConnection(tenantInfo);
    }

    @Override
    public Connection getConnection() throws SQLException {

        // 获取企业ID
        Long tenantId = tryFetchTenantId();

        // 根据企业ID取得数据源
        Connection conn = null;

        if (DataSourceTypeEnum.POOL_MASTER_FORCE.equals(DatabaseRouter.getDsTypeLocal())) {
            DataSource defaultDataSource = getResolvedDefaultDataSource();
            if (null == defaultDataSource) {
                throw new BizException("当前服务未设置主数据源，请指定 master 数据源！");
            }
            return defaultDataSource.getConnection();
        }

        if (tenantId != null && tenantId > 0) {
            conn = doGetTenantConnection(tenantId);
        } else {

            log.info("动态数据源无法获得当前企业ID！[tenantId]:{}", tenantId);
            throw new TenantNotExistException("当前企业Id为空");
        }

        //企业id不为null
        if (conn != null) {
            return conn;
        }

        //连接为空，并且企业信息不存在，抛出企业不存在的异常。
        if (checkTenantNotExist(tenantId)) {
            log.error("DynamicDataSource get connection 为 null! 不存在企业id为:{} 的企业信息!", tenantId);
            throw new TenantNotExistException(tenantId);
        } else {
            log.error("DynamicDataSource get connection 为 null! 获取不到企业id为:{} 的数据库连接!", tenantId);
            throw new TenantNotExistException("获取不到企业id为:" + tenantId + " 的数据库连接");
        }

    }

    /**
     * 自动调用该方法来重新设置数据源， null 表示使用默认数据源 resolvedDefaultDataSource
     * tip：已重写父类的getConnection()方法 此方法已不会被调用
     */
    @Override
    protected Object determineCurrentLookupKey() {
        log.info("determineCurrentLookupKey");
        return null;
    }

    /**
     * 获取企业数据源连接
     *
     * @param tenantId 企业Id
     * @return 数据源连接
     * @throws SQLException 异常
     */
    private Connection doGetTenantConnection(long tenantId) throws SQLException {

        TenantDataSources tenantDataSources = TENANT_DATA_SOURCES_MAP.get(String.valueOf(tenantId));

        if (tenantDataSources == null) {
            // 初始化主库和从库
            tenantDataSources = RegisterTenantDatasource.blockAndInitTenantDatasource(tenantId);

            if (tenantDataSources == null) {
                log.error("getConnectionFromPool tenantDss 数据源连接获取失败！[tenantId]:{}", tenantId);
                return null;
            }
        }

        DataSourceTypeEnum dsType = DatabaseRouter.determineDataSourceType();

        return tenantDataSources.getConnection(dsType);
    }

    /**
     * 检测企业信息是否不存在
     *
     * @param tenantId 企业Id
     * @return 企业是否存在
     */
    private boolean checkTenantNotExist(long tenantId) {

        try {
            MasterTenantHandler.getTenantInfo(tenantId);
            return false;
        } catch (Exception e) {
            log.error("checkTenantNotExist exception,[tenantId]:{}", tenantId, e);
            //抛出异常认为企业信息获取不到，即使是因为网络原因获取不到的，也没关系，因为connection 为空
            return true;
        }
    }

}
