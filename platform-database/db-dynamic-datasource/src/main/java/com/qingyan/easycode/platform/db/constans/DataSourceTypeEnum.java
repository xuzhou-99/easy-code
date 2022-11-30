package com.qingyan.easycode.platform.db.constans;

/**
 * 数据源类型
 *
 * @author xuzhou
 */
public enum DataSourceTypeEnum {

    /**
     * 主库
     */
    MASTER("master"),
    /**
     * 主库-连接池
     */
    POOL_MASTER("masterDsPool"),
    /**
     * 主库-jdbc连接
     */
    JDBC_MASTER("masterDsJdbc"),
    /**
     * 强制使用主库连接池, 无视企业的设置参数是否使用连接池
     */
    POOL_MASTER_FORCE("masterDsPoolForce"),
    ;

    /**
     * 数据源标识
     */
    private final String dsTag;

    DataSourceTypeEnum(String dsTag) {
        this.dsTag = dsTag;
    }

    /**
     * 判断是否jdbc连接
     *
     * @param enumType 数据源类型
     * @return jdbc连接
     */
    public static boolean isJdbcDsType(DataSourceTypeEnum enumType) {

        if (enumType == null) {
            return false;
        }

        return enumType == DataSourceTypeEnum.JDBC_MASTER;
    }

    public String getDsTag() {
        return dsTag;
    }


}