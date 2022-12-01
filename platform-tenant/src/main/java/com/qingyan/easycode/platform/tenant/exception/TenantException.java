package com.qingyan.easycode.platform.tenant.exception;

import com.qingyan.easycode.platform.core.exception.BizException;
import com.qingyan.easycode.platform.tenant.enums.TenantExceptionEnum;

/**
 * @author xuzhou
 * @since 2022/11/16
 */
public class TenantException extends BizException {


    public TenantException(String msg) {
        super(msg);
    }

    public TenantException(TenantExceptionEnum exceptionEnum, String msg) {
        super(exceptionEnum, msg);
    }
}
