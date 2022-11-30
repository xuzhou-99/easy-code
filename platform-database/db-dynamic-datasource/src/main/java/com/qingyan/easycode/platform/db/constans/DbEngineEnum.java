package com.qingyan.easycode.platform.db.constans;

/**
 * @author xuzhou
 * @since 2022/11/16
 */
public enum DbEngineEnum {

    /**
     * mysql
     */
    mysql("mysql", "com.mysql.cj.jdbc.Driver");

    /**
     * 数据库名称
     */
    private final String name;
    /**
     * 数据库驱动类
     */
    private final String driverClass;


    DbEngineEnum(String name, String driverClass) {
        this.name = name;
        this.driverClass = driverClass;
    }

    public String getName() {
        return name;
    }

    public String getDriverClass() {
        return driverClass;
    }
}
