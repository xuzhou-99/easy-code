package com.qingyan.easycode.platform.tenant.enums;

/**
 * @author xuzhou
 * @since 2022/11/17
 */
public enum TenantTypeEnum {

    /**
     * 0 付费客户 1 免费客户
     */
    TENANT_TYPE_VIP("0"),
    TENANT_TYPE_FREE("1"),
    ;

    private final String code;

    TenantTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
