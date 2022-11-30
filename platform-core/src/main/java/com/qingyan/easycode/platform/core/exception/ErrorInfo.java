package com.qingyan.easycode.platform.core.exception;

/**
 * @author xuzhou
 * @since 2022/11/30
 */
public interface ErrorInfo {

    /**
     * 异常码
     *
     * @return int
     */
    int code();

    /**
     * 异常描述
     *
     * @return String
     */
    String message();

}
