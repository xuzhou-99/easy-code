package com.qingyan.easycode.platform.db.tenant;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingyan.easycode.platform.db.DatabaseRouter;
import com.qingyan.easycode.platform.db.constans.DataSourceTypeEnum;
import com.qingyan.easycode.platform.tenant.TenantHandler;
import com.qingyan.easycode.platform.tenant.entity.TenantInfo;
import com.qingyan.easycode.platform.tenant.exception.TenantException;

/**
 * 主数据源租户处理
 *
 * @author xuzhou
 * @since 2022/11/30
 */
public class MasterTenantHandler {

    private static final Logger log = LoggerFactory.getLogger(MasterTenantHandler.class);

    private MasterTenantHandler() {

    }

    /**
     * 删除企业 DbInfo 的缓存key,删除数据源时，也要删除这个缓存key
     *
     * @param tenantId 企业Id
     */
    public static void deleteDbInfoCache(long tenantId) {
        TenantHandler.deleteDbInfoCache(tenantId);
    }

    /**
     * 获取企业的信息
     *
     * @param tenantId 企业ID
     * @return 企业信息
     */
    public static TenantInfo getTenantInfo(Long tenantId) {

        log.info("====== 切换到 Master 数据源获取企业租户信息 ======");
        DatabaseRouter.setDataSourceType(DataSourceTypeEnum.POOL_MASTER_FORCE);

        try {
            return TenantHandler.getTenantInfo(tenantId);
        } catch (TenantException e) {
            log.error("从 Master 数据源获取企业租户信息失败：{}", e.getMessage());
            throw new TenantException(e.getMessage());
        } finally {
            DatabaseRouter.removeOne();
            log.info("====== 移除 Master 数据源 ======");
        }
    }


    /**
     * 根据企业编码查询企业信息列表
     *
     * @param tenantCode 企业编码
     * @return 企业信息列表
     */
    public static TenantInfo doGetTenantByCode(String tenantCode) {

        log.info("====== 切换到 Master 数据源获取企业租户信息 ======");
        DatabaseRouter.setDataSourceType(DataSourceTypeEnum.POOL_MASTER_FORCE);

        try {
            return TenantHandler.doGetTenantByCode(tenantCode);
        } catch (TenantException e) {
            log.error("从 Master 数据源获取企业租户信息失败：{}", e.getMessage());
            throw new TenantException(e.getMessage());
        } finally {
            DatabaseRouter.removeOne();
            log.info("====== 移除 Master 数据源 ======");
        }

    }

    /**
     * 获取所有的企业Id
     *
     * @return 企业Id集合
     */
    public static List<String> getAllTenantIds() {

        try {
            log.info("====== 切换到 Master 数据源获取企业租户信息 ======");
            DatabaseRouter.setDataSourceType(DataSourceTypeEnum.POOL_MASTER_FORCE);
            return TenantHandler.getAllTenantIds();
        } catch (TenantException e) {
            log.error("从 Master 数据源获取企业租户信息失败：{}", e.getMessage());
            throw new TenantException(e.getMessage());
        } finally {
            DatabaseRouter.removeOne();
            log.info("====== 移除 Master 数据源 ======");
        }
    }
}
