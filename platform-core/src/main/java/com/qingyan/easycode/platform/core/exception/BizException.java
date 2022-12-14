package com.qingyan.easycode.platform.core.exception;

import com.qingyan.easycode.platform.core.exception.enums.BaseExceptionEnum;

/**
 * 业务异常
 *
 * @author xuzhou
 * @since 2022/12/1
 */
public class BizException extends RuntimeException {

    /**
     * 异常信息
     */
    protected final ErrorInfo errorInfo;

    /**
     * 无参构造方法默认为程序错误
     */
    public BizException() {
        super(BaseExceptionEnum.ERROR.message());
        this.errorInfo = BaseExceptionEnum.ERROR;
    }

    public BizException(String message) {
        super(message);
        this.errorInfo = BaseExceptionEnum.ERROR;
    }

    public BizException(ErrorInfo errorInfo) {
        super(errorInfo.message());
        this.errorInfo = errorInfo;
    }

    public BizException(ErrorInfo errorInfo, String message) {
        super(message);
        this.errorInfo = errorInfo;
    }


    /**
     * 根据异常类型获取code
     *
     * @param e 异常
     * @return int
     */
    public static int getCode(Exception e) {
        return e instanceof BizException ? ((BizException) e).getErrorInfo().code() : BaseExceptionEnum.ERROR.code();
    }

    /**
     * 获取异常信息
     *
     * @return ErrorInfo
     */
    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
}
