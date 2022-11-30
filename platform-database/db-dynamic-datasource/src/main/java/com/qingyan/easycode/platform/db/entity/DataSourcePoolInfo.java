package com.qingyan.easycode.platform.db.entity;


import java.io.Serializable;

/**
 * 数据源池信息
 *
 * @author xuzhou
 */
public class DataSourcePoolInfo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3233403652689546607L;

    /**
     * 主数据源节点
     */
    private DataSourceUnit masterDataSource;


    public DataSourcePoolInfo(DataSourceUnit master) {
        this.masterDataSource = master;
    }

    public DataSourceUnit getMasterDataSource() {
        return masterDataSource;
    }

    public void setMasterDataSource(DataSourceUnit masterDataSource) {
        this.masterDataSource = masterDataSource;
    }
}