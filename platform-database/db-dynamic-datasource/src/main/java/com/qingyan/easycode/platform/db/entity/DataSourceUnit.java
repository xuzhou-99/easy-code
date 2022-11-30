package com.qingyan.easycode.platform.db.entity;

import javax.sql.DataSource;

/**
 * 数据源
 *
 * @author xuzhou
 */
public class DataSourceUnit {


    /**
     * 数据源别名
     */
    private String aliasName;

    /**
     * 数据源
     */
    private DataSource dataSource;


    public DataSourceUnit(String aliasName, DataSource dataSource) {

        this.aliasName = aliasName;

        this.dataSource = dataSource;
    }


    public String getAliasName() {
        return aliasName;
    }


    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }


    public DataSource getDataSource() {
        return dataSource;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}