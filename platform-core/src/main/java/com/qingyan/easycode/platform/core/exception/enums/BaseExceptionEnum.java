package com.qingyan.easycode.platform.core.exception.enums;

import com.qingyan.easycode.platform.core.exception.ErrorInfo;

/**
 * @author xuzhou
 * @since 2022/12/1
 */
public enum BaseExceptionEnum implements ErrorInfo {

    /**
     * 待处理
     */
    WAIT(2, "待处理"),

    /**
     * 成功
     */
    SUCCESS(1, "SUCCESS"),

    /**
     * 程序错误
     */
    ERROR(0, "程序错误"),

    // ------------------------------------------------------------------

    ;

    /**
     * 编码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    BaseExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转换成enum
     *
     * @param code 错误码
     * @return HandleExceptionEnum
     */
    public static BaseExceptionEnum codeOf(int code) {
        for (BaseExceptionEnum item : BaseExceptionEnum.values()) {
            if (item.code() == code) {
                return item;
            }
        }
        return null;
    }

    /**
     * 指定code是否在枚举之内
     *
     * @param code 错误码
     * @return boolean
     */
    public static boolean contain(int code) {
        for (BaseExceptionEnum item : BaseExceptionEnum.values()) {
            if (item.code() == code) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
