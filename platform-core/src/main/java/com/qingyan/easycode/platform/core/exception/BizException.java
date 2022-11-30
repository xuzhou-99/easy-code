package com.qingyan.easycode.platform.core.exception;

/**
 * @author xuzhou
 * @since 2022/11/30
 */
public class BizException extends RuntimeException {

    /**
     * 异常信息
     */
    private final ErrorInfo errorInfo;

    /**
     * 无参构造方法默认为程序错误
     */
    public BizException() {
        super(HandleExceptionEnum.ERROR.message());
        this.errorInfo = HandleExceptionEnum.ERROR;
    }

    public BizException(HandleExceptionEnum handleExceptionEnum) {
        super(handleExceptionEnum.message());
        this.errorInfo = handleExceptionEnum;
    }

    public BizException(String message) {
        super(message);
        this.errorInfo = HandleExceptionEnum.ERROR;
    }

    public BizException(HandleExceptionEnum handleExceptionEnum, String message) {
        super(message);
        this.errorInfo = handleExceptionEnum;
    }

    /**
     * 根据异常类型获取code
     *
     * @param e 异常
     * @return int
     */
    public static int getCode(Exception e) {
        return e instanceof BizException ? ((BizException) e).getErrorInfo().code() : HandleExceptionEnum.ERROR.code();
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
