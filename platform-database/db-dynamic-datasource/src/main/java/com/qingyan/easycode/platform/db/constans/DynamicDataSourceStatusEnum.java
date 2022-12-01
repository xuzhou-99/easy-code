package com.qingyan.easycode.platform.db.constans;

/**
 * 动态数据源状态
 *
 * @author xuzhou
 * @since 2022/11/17
 */
public enum DynamicDataSourceStatusEnum {

    /**
     * 数据源连接切换失败
     */
    DATASOURCE_SUCCESS(0),
    /**
     * 数据源连接切换失败
     */
    DATASOURCE_FAIL(1),
    /**
     * 租户不存在
     */
    DATASOURCE_TENANT_NULL(101),
    /**
     * 租户已停用
     */
    DATASOURCE_TENANT_DEACTIVATED(102),
    /**
     * 服务异常
     */
    DATASOURCE_SEVER_ERROR(103),


    ;


    /**
     * 状态码
     */
    private final Integer code;

    DynamicDataSourceStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
