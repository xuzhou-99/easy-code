package com.qingyan.easycode.platform.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.qingyan.easycode.platform.core.lock.SegmentLock;
import com.qingyan.easycode.platform.db.constans.DataSourceTypeEnum;
import com.qingyan.easycode.platform.db.entity.DataSourcePoolInfo;
import com.qingyan.easycode.platform.db.entity.DataSourceUnit;
import com.qingyan.easycode.platform.db.entity.TenantDataSources;
import com.qingyan.easycode.platform.db.exception.DynamicDataBaseException;
import com.qingyan.easycode.platform.db.tenant.MasterTenantHandler;
import com.qingyan.easycode.platform.tenant.constans.TenantConstants;
import com.qingyan.easycode.platform.tenant.entity.DbInfo;
import com.qingyan.easycode.platform.tenant.entity.TenantInfo;
import com.qingyan.easycode.platform.tenant.exception.TenantException;

/**
 * 企业数据源注册
 */
public class RegisterTenantDatasource {

    public static final String connectionTestBefore = "0";
    public static final int connectionSleepKeepTime = Integer.parseInt("2000");
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterTenantDatasource.class);
    /**
     * hash 锁
     */
    private static final SegmentLock<String> segmentLock = new SegmentLock<>(30, false);

    private RegisterTenantDatasource() {

    }

    /**
     * 根据企业切换数据源,当数据源池中已经存在时直接获取，不存在时创建新数据源.
     * 这里会同时创建主库和从库
     *
     * @param tenantId 企业ID
     * @return true/false 是否切换数据源
     */
    public static boolean getTenantDatasource(Long tenantId) {

        try {
            TenantInfo tenantInfo = MasterTenantHandler.getTenantInfo(tenantId);
            return getTenantDatasource(tenantInfo);
        } catch (Exception e) {
            LOGGER.error("根据企业ID[{}]获取数据源失败，原因：{}", tenantId, e.getMessage(), e);
            return false;
        }

    }

    /**
     * 根据企业信息切换数据源，当数据源池中已经存在时比较连接池是否有变化有变化时创建新连接池并覆盖原来的连接，不存在时创建新数据源
     *
     * @param tenantInfo 企业信息
     * @return true/false 数据源是否切换
     */
    public static boolean getTenantDatasource(TenantInfo tenantInfo) {

        if (tenantInfo == null) {
            throw new DynamicDataBaseException("TenantInfo info can not be null!");
        }
        Long tenantId = tenantInfo.getId();
        try {

            setCurrentTenantId(tenantId);

            LOGGER.info("[{}] dataSource exists status :{}", tenantId, DynamicDataSource.isDataSourceExist(tenantId));

            // 如果数据源未初始化，那么执行初始化
            if (!DynamicDataSource.isDataSourceExist(tenantId)) {
                blockAndInitTenantDatasource(tenantId);
            }

            return true;
        } catch (Exception e) {
            LOGGER.error("根据企业ID[{}]获取数据源失败，原因：{}", tenantId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * 设置 tenantId 到当前的 ThreadLocal 中
     *
     * @param tenantId
     */
    private static void setCurrentTenantId(Long tenantId) {
        // 设置当前企业数据源标志到session中

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            // 获取 HttpServletRequest
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

            if (request == null) {
                LOGGER.info("request is null, 设置企业信息到线程环境中");
                DynamicDataSource.setCurTenant(tenantId);
                return;
            }

            HttpSession session = request.getSession(false);

            // 请求过来的话，那么都设置 ThreadLocal
            DynamicDataSource.setCurTenant(tenantId);

            if (session != null) {
                Long oldTenantId = (Long) session.getAttribute(TenantConstants.TENANT_ID);
                LOGGER.debug("注册数据库连接时重新设置当前用户企业ID：{}，OLD：{}", tenantId, oldTenantId);

                if (null != oldTenantId && !tenantId.equals(oldTenantId)) {
                    LOGGER.info("检查到企业ID存在差异，{} => {}", oldTenantId, tenantId);
                }
                session.setAttribute(TenantConstants.TENANT_ID, tenantId);
            }
        }
    }

    /**
     * 根据企业ID创建数据源连接池，主库和从库一起创建，返回主库连接
     *
     * @param tenantId 企业ID
     * @return 数据源对象
     */
    public static DataSource createTenantDatasource(Long tenantId) {

        TenantDataSources ds = DynamicDataSource.getTenantDataSources(tenantId);

        if (ds == null) {
            // 创建企业所有的数据源连接
            ds = blockAndInitTenantDatasource(tenantId);
        }
        if (ds == null) {
            return null;
        }
        return ds.getDataSourceFromConnPool(DataSourceTypeEnum.POOL_MASTER);
    }

    /**
     * 创建企业所有的数据源连接
     *
     * @param tenantId
     */
    public static TenantDataSources blockAndInitTenantDatasource(Long tenantId) {

        boolean isLocked = false;

        try {
            // 尝试hash锁
            isLocked = segmentLock.lock(tenantId.toString(), 30, TimeUnit.SECONDS);

            // 等待了30s没获取到锁
            if (!isLocked) {
                LOGGER.error("blockAndInitTenantDatasource getLock failed,[tenantId]:{}", tenantId);
                throw new DynamicDataBaseException("init datasource failed,[tenantId]:" + tenantId);
            }

            TenantDataSources tenantDataSources = DynamicDataSource.getTenantDataSources(tenantId);

            // 获取到锁之后，发现已经被别人创建好了，那么直接获取，无需重复创建连接池
            if (tenantDataSources != null) {
                return tenantDataSources;
            }

            // 初始化数据源
            tenantDataSources = initTenantDatasource(tenantId);

            if (tenantDataSources != null) {
                // 注册到动态数据源
                DynamicDataSource.registerTenantDataSources(tenantId, tenantDataSources);
                LOGGER.info("企业[{}]创建数据源成功", tenantId);
            }

            return tenantDataSources;
        } catch (Exception e) {
            LOGGER.error("根据企业ID[{}]获取数据源失败，原因：{}", tenantId, e.getMessage(), e);
            return null;
        } finally {
            if (isLocked) {
                segmentLock.unlock(tenantId.toString());
            }
        }
    }

    /**
     * 初始化企业的数据源
     *
     * @param tenantId
     * @return
     */
    private static TenantDataSources initTenantDatasource(long tenantId) {

        TenantInfo tenantInfo = MasterTenantHandler.getTenantInfo(tenantId);

        // 校验是否正常参数
        if (checkIsIllegalTenant(tenantInfo)) {
            return null;
        }

        // 这个企业设置为不使用连接池
        if (!tenantInfo.getDbInfo().isUseConnPool()) {
            return new TenantDataSources(tenantId, tenantInfo.getDbInfo());
        }

        DataSourcePoolInfo dataSourcePoolInfo = createDataSourcePoolInfo(tenantInfo.getDbInfo());
        if (dataSourcePoolInfo == null) {
            return null;
        }

        return new TenantDataSources(tenantId, tenantInfo.getDbInfo(), dataSourcePoolInfo.getMasterDataSource());
    }

    /**
     * 懒加载连接池
     *
     * @param dbinfo
     * @return
     */
    public static DataSourcePoolInfo lazyCreateDataSourcePoolInfo(DbInfo dbinfo) {

        boolean isLocked = false;

        try {
            // 尝试hash锁
            isLocked = segmentLock.lock(dbinfo.getTenantId().toString(), 30, TimeUnit.SECONDS);

            // 等待了30s没获取到锁
            if (!isLocked) {

                LOGGER.error("lazyCreateDataSourcePoolInfo getLock failed,[tenantId]: {}", dbinfo.getTenantId().toString());
                throw new DynamicDataBaseException("init datasource failed,[tenantId]:" + dbinfo.getTenantId().toString());
            }

            TenantDataSources tenantDataSources = DynamicDataSource.getTenantDataSources(dbinfo.getTenantId());

            // 获取到锁之后，发现已经被别人创建好了，那么直接获取，无需重复创建连接池
            if (tenantDataSources == null) {
                throw new DynamicDataBaseException("lazyCreateDataSourcePoolInfo  failed,[tenantId]:" + dbinfo.getTenantId().toString());
            }

            return createDataSourcePoolInfo(dbinfo);

        } finally {
            if (isLocked) {
                segmentLock.unlock(dbinfo.getTenantId().toString());
            }
        }

    }

    /**
     * 创建连接池的连接
     *
     * @param dbinfo
     * @return
     */
    private static DataSourcePoolInfo createDataSourcePoolInfo(DbInfo dbinfo) {

        try {

            /***************************初始化master*******************************/
            // master的url
            String masterUrl = dbinfo.getJdbcUrl();
            // master的别名
            String masterAlias = String.valueOf(dbinfo.getTenantId());
            DataSourceUnit masterDsUnit = createDatasource(dbinfo, masterUrl, masterAlias);

            return new DataSourcePoolInfo(masterDsUnit);
        } catch (Exception e) {
            LOGGER.warn("获取企业{}数据源失败,原因：{}", dbinfo.getTenantId(), e.getMessage());
            return null;
        }

    }

    /**
     * 创建数据源
     *
     * @param dbInfo
     * @param dbUrl
     * @param alias
     * @return
     */
    private static DataSourceUnit createDatasource(DbInfo dbInfo, String dbUrl, String alias) {

        Assert.notNull(dbInfo, "企业数据源信息不能为空");

        // 根据数据源信息切换数据库
        ProxoolDataSource pds = new ProxoolDataSource();
        pds.setAlias(alias);
        pds.setDriverUrl(dbUrl);
        pds.setDriver(dbInfo.getJdbcDriver());
        pds.setUser(dbInfo.getDbUsername());
        pds.setPassword(dbInfo.getDbPassword());
        pds.setMinimumConnectionCount(dbInfo.getMinConnCount());
        pds.setMaximumConnectionCount(dbInfo.getMaxConnCount());
        // 空闲数据库连接断开的时间使用数据库的sleep_keep_time
        pds.setHouseKeepingSleepTime(connectionSleepKeepTime);
        // 同一时刻允许申请最大连接数
        pds.setSimultaneousBuildThrottle(50);
        // 增加数据库连接重试机制
        if ("1".equals(connectionTestBefore)) {
            pds.setTestBeforeUse(true);
        } else {
            pds.setTestBeforeUse(false);
        }
        pds.setHouseKeepingTestSql("select 1");
        // 连接最大保持时间
        pds.setMaximumConnectionLifetime(dbInfo.getMaxConnLifetime());

        return new DataSourceUnit(alias, pds);
    }

    /**
     * 检查是否无效的非法企业信息
     *
     * @param tenantInfo
     * @return
     */
    private static boolean checkIsIllegalTenant(TenantInfo tenantInfo) {

        if (tenantInfo == null) {
            LOGGER.warn("创建Proxool数据源失败，原因：企业信息为空！");
            return true;
        }

        Long tenantId = tenantInfo.getId();
        if (tenantId == null) {
            LOGGER.warn("创建Proxool数据源失败，原因：企业标识为空，无法设置proxool数据源别名！");
            return true;
        }

        if (tenantInfo.getDbInfo() == null) {
            LOGGER.warn("创建Proxool数据源失败，原因：dbinfo为空，无法设置proxool数据源属性！");
            return true;
        }

        //增加升级中状态
        if (!TenantConstants.STATUS_RUN.equals(tenantInfo.getStatus())) {
            LOGGER.warn("创建Proxool数据源失败，原因：企业标识状态不是启动状态！");
            throw new TenantException("企业标识状态不是启动状态！");
        }

        return false;

    }


    /**
     * 根据直接创建 企业信息 DbInfo
     *
     * @param tenantId 企业id
     * @return 数据库连接
     */
    public static Connection createJdbcConnectionByDbInfo(long tenantId) {

        TenantInfo tenantInfo = getTenantInfo(tenantId);
        if (tenantInfo == null) {
            return null;
        }

        try {
            String dbUrl = tenantInfo.getDbInfo().getJdbcUrl();
            Properties props = new Properties();
            props.setProperty("user", tenantInfo.getDbInfo().getDbUsername());
            props.setProperty("password", tenantInfo.getDbInfo().getDbPassword());
            // socket建立连接 1分钟，= 60s
            props.setProperty("connectTimeout", "60");
            // default as 10分钟，= 600s
            props.setProperty("socketTimeout", "600");
            return DriverManager.getConnection(dbUrl, props);
        } catch (SQLException e) {
            LOGGER.error("DriverManager 创建 JDBC 企业{}连接失败,原因：{}", tenantId, e.getMessage());
        }

        return null;
    }

    /**
     * 获取企业信息
     *
     * @param tenantId 企业Id
     * @return 企业信息
     */
    private static TenantInfo getTenantInfo(long tenantId) {

        try {
            return MasterTenantHandler.getTenantInfo(tenantId);
        } catch (Exception e) {
            LOGGER.warn("获取JDBC 企业{}数据源失败,原因：{}", tenantId, e.getMessage());
        }
        return null;
    }

}
