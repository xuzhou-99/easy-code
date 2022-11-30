package com.qingyan.easycode.platform.tenant.pojo;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 多数据源信息
 *
 * @TableName sys_database_info
 */
@TableName("sys_database_info")
public class SysDatabaseInfo implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 创建时间
     */

    private Date createTime;
    /**
     * 创建人
     */

    private Long createUser;
    /**
     * 修改时间
     */

    private Date updateTime;
    /**
     * 修改人
     */

    private Long updateUser;
    /**
     * 租户Id
     */

    private Long tenantId;
    /**
     * 数据库名称（英文名称）
     */

    private String dbName;
    /**
     * jdbc的驱动类型
     */

    private String jdbcDriver;
    /**
     * jdbc的url
     */

    private String jdbcUrl;
    /**
     * 数据库连接的账号
     */

    private String dbUsername;
    /**
     * 数据库连接密码
     */

    private String dbPassword;
    /**
     * 数据库的schema名称，每种数据库的schema意义都不同
     */

    private String schemaName;
    /**
     * host
     */

    private String dbHost;
    /**
     * 端口
     */

    private String dbPort;
    /**
     * 最大连接数
     */
    private Integer maxConnCount;
    /**
     * 最小连接数
     */
    private Integer minConnCount;
    /**
     * 最大连接时长
     */
    private Integer maxConnLifetime;
    /**
     * 连接最长存活时间
     */

    private Integer keepSleepTime;
    /**
     * 数据源状态：1-正常，2-无法连接
     */

    private Integer statusFlag;
    /**
     * 连接失败原因
     */

    private String errorDescription;
    /**
     * 备注，摘要
     */

    private String remarks;
    /**
     * 是否删除，Y-被删除，N-未删除
     */

    private String delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public Integer getMaxConnCount() {
        return maxConnCount;
    }

    public void setMaxConnCount(Integer maxConnCount) {
        this.maxConnCount = maxConnCount;
    }

    public Integer getMinConnCount() {
        return minConnCount;
    }

    public void setMinConnCount(Integer minConnCount) {
        this.minConnCount = minConnCount;
    }

    public Integer getMaxConnLifetime() {
        return maxConnLifetime;
    }

    public void setMaxConnLifetime(Integer maxConnLifetime) {
        this.maxConnLifetime = maxConnLifetime;
    }

    public Integer getKeepSleepTime() {
        return keepSleepTime;
    }

    public void setKeepSleepTime(Integer keepSleepTime) {
        this.keepSleepTime = keepSleepTime;
    }

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
