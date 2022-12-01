package com.qingyan.easycode.platform.tenant.enums;

import com.qingyan.easycode.platform.core.exception.ErrorInfo;

/**
 * @author xuzhou
 * @since 2022/12/1
 */
public enum TenantExceptionEnum implements ErrorInfo {

    ;

    /**
     * 异常码
     *
     * @return int
     */
    @Override
    public int code() {
        return 0;
    }

    /**
     * 异常描述
     *
     * @return String
     */
    @Override
    public String message() {
        return null;
    }
}
