package com.qingyan.easycode.platform.tenant.constans;

/**
 * @author xuzhou
 * @since 2022/11/16
 */
public class TenantConstants {

    public static final String TENANT_ID = "s_tenantId";
    /**
     * 基站定位 Google API解析
     */
    public static final String LOCATION_POLICY_GOOGLE = "google";
    /**
     * 基站定位 Baidu API解析
     */
    public static final String LOCATION_POLICY_BAIDU = "baidu";
    /**
     * SI定位
     */
    public static final String LOCATION_POLICY_SI = "si";
    /**
     * 运营商类型-自运营
     */
    public static final String OWNOPERATIONS = "0";
    /**
     * 运营商类型-运营商接入
     */
    public static final String CARRIERACCESS = "1";
    /**
     * 账户状态 停用 0
     */
    public static final String STATUS_STOP = "0";
    /**
     * 账户状态 启用 1
     */
    public static final String STATUS_RUN = "1";
    /**
     * 账户状态 禁用 2
     */
    public static final String STATUS_FORBIDDEN = "2";
    /**
     * 账户状态 销户 3
     */
    public static final String STATUS_CANCELLATION = "3";
    /**
     * 账户状态 升级中 4
     */
    public static final String STATUS_UPGRADING = "4";

    private TenantConstants() {

    }
}
