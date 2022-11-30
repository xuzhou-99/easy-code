package com.qingyan.easycode.platform.core.exception;

/**
 * @author xuzhou
 * @since 2022/11/30
 */
public enum HandleExceptionEnum implements ErrorInfo {

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


    /**
     * 公共 - rds配置未取到
     */
    C_GENERATE_RDS_NOT_FOUND(1001, "rds配置未取到"),

    /**
     * 公共 - 租户代码为空
     */
    C_GENERATE_TENANT_CODE_IS_BLANK(1002, "租户代码为空"),

    /**
     * 公共 - 数据源配置不存在
     */
    C_GENERATE_DATA_SOURCE_NOT_EXIST(1003, "数据源配置不存在"),

    /**
     * 公共 - 数据源名称为空
     */
    C_GENERATE_DATA_SOURCE_NAME_IS_EMPTY(1004, "数据源名称为空"),

    /**
     * 公共 - 数据源名称为空
     */
    C_GENERATE_DATA_SOURCE_SWITCH_FAIL(1005, "数据源切换失败"),


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

    HandleExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转换成enum
     *
     * @param code 错误码
     * @return HandleExceptionEnum
     */
    public static HandleExceptionEnum codeOf(int code) {
        for (HandleExceptionEnum item : HandleExceptionEnum.values()) {
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
        for (HandleExceptionEnum item : HandleExceptionEnum.values()) {
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
