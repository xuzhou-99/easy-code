package com.qingyan.easycode.platform.tenant.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DBInfo.
 *
 * @author xuzhou
 */
@Setter
@Getter
public class DbInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    private Long tenantId;
    /**
     * 企业名称.
     */
    private String tenantCode;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 数据库URL.
     */
    private String jdbcUrl;
    /**
     * 数据库访问用户名.
     */
    private String dbUsername;
    /**
     * 数据库访问密码.
     */
    private String dbPassword;
    /**
     * 数据库驱动.
     */
    private String jdbcDriver;
    /**
     * 模式名
     */
    private String schemaName;
    /**
     * 最大连接数.
     */
    private Integer maxConnCount = 100;
    /**
     * 最小连接数.
     */
    private Integer minConnCount = 1;
    /**
     * 空闲回收时间.
     */
    private Integer maxConnLifetime = 3600;

    private String dbHost;

    private Integer dbPort;
    private String dbBackupHost;
    private Integer dbBackupPort;

    /**
     * 是否使用连接池
     */
    private boolean isUseConnPool;


}
