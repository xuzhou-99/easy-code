package com.qingyan.easycode.platform.tenant.exception;

/**
 * @author xuzhou
 * @since 2022/11/16
 */
public class TenantNotExistException extends RuntimeException {
    public TenantNotExistException(long tenantId) {
        super("未查询到企业id为:" + tenantId + " 的企业信息!");
    }

    public TenantNotExistException(String message) {
        super(message);
    }
}
