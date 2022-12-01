package com.qingyan.easycode.platform.core.exception;

import com.qingyan.easycode.platform.core.exception.enums.BaseExceptionEnum;

/**
 * 封装定义异常
 *
 * @author xuzhou
 * @since 2022/11/30
 */
public class BaseException extends Exception {

    /**
     * 异常信息
     */
    protected final ErrorInfo errorInfo;

    /**
     * 无参构造方法默认为程序错误
     */
    public BaseException() {
        super(BaseExceptionEnum.ERROR.message());
        this.errorInfo = BaseExceptionEnum.ERROR;
    }

    public BaseException(String message) {
        super(message);
        this.errorInfo = BaseExceptionEnum.ERROR;
    }

    public BaseException(ErrorInfo errorInfo) {
        super(errorInfo.message());
        this.errorInfo = errorInfo;
    }

    public BaseException(ErrorInfo errorInfo, String message) {
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
        return e instanceof BaseException ? ((BaseException) e).getErrorInfo().code() : BaseExceptionEnum.ERROR.code();
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
