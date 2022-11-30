package com.qingyan.easycode.platform.db.tenant;

import java.util.List;

import com.qingyan.easycode.platform.db.DatabaseRouter;
import com.qingyan.easycode.platform.db.constans.DataSourceTypeEnum;
import com.qingyan.easycode.platform.tenant.TenantHandler;
import com.qingyan.easycode.platform.tenant.entity.TenantInfo;

/**
 * 主数据源租户处理
 *
 * @author xuzhou
 * @since 2022/11/30
 */
public class MasterTenantHandler {

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
        DatabaseRouter.setDataSourceType(DataSourceTypeEnum.MASTER);

        TenantInfo tenantInfo = TenantHandler.getTenantInfo(tenantId);

        DatabaseRouter.removeOne();

        return tenantInfo;
    }

    /**
     * 获取所有的企业Id
     *
     * @return 企业Id集合
     */
    public static List<String> getAllTenantIds() {

        DatabaseRouter.setDataSourceType(DataSourceTypeEnum.MASTER);

        List<String> allTenantIds = TenantHandler.getAllTenantIds();

        DatabaseRouter.removeOne();

        return allTenantIds;
    }
}
