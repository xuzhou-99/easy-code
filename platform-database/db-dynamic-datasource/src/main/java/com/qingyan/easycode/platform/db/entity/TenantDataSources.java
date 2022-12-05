package com.qingyan.easycode.platform.db.entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.qingyan.easycode.platform.db.RegisterTenantDatasource;
import com.qingyan.easycode.platform.db.constans.DataSourceTypeEnum;
import com.qingyan.easycode.platform.tenant.entity.DbInfo;

/**
 * @author xuzhou
 */
public class TenantDataSources {

    private static final Logger logger = LoggerFactory.getLogger(TenantDataSources.class);

    private final long tenantId;

    /**
     * 缓存对象 dbInfo
     */
    private final DbInfo dbInfo;
    private final ReentrantLock lock = new ReentrantLock(false);
    /**
     * 主数据源节点
     */
    private DataSourceUnit masterDataSource;

    public TenantDataSources(long tenantId, DbInfo dbinfo) {
        this.tenantId = tenantId;
        this.dbInfo = dbinfo;
    }

    public TenantDataSources(long tenantId, DbInfo dbinfo, DataSourceUnit master) {
        this.tenantId = tenantId;
        this.masterDataSource = master;
        this.dbInfo = dbinfo;
    }

    public long getTenantId() {
        return tenantId;
    }

    public DbInfo getDbInfo() {
        return dbInfo;
    }

    public DataSourceUnit getMasterDataSource() {
        return masterDataSource;
    }

    public void setMasterDataSource(DataSourceUnit masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    /**
     * 获取连接
     *
     * @param dsEnum
     * @return
     * @throws SQLException
     */
    public Connection getConnection(DataSourceTypeEnum dsEnum) throws SQLException {

        if (dsEnum == DataSourceTypeEnum.POOL_MASTER_FORCE) {
            return doGetConnectionFromPool(dsEnum);
        }

        // 是否使用jdbc的类型
        boolean useJdbc = DataSourceTypeEnum.isJdbcDsType(dsEnum);

        // 注明使用jdbc或者 企业设置使用jdbc了，那么使用jdbc
        if (useJdbc || !isTenantUseConnPool()) {
            return doGetConnectionFromJdbc(dsEnum);
        } else {
            return doGetConnectionFromPool(dsEnum);
        }

    }

    private Connection doGetConnectionFromJdbc(DataSourceTypeEnum dsEnum) {

        // 根据企业ID取得数据源
        Connection conn = RegisterTenantDatasource.createJdbcConnectionByDbInfo(tenantId, dbInfo);

        if (conn != null) {
            logger.info("dynamic datasource getConnection from jdbc success, [tenantId] {} ,[dsType]:{}", tenantId
                    , DataSourceTypeEnum.POOL_MASTER);
        } else {
            logger.info("dynamic datasource getConnection from jdbc failed, [tenantId] {} ,[dsType]:{}", tenantId
                    , DataSourceTypeEnum.POOL_MASTER);
        }

        return conn;
    }

    private Connection doGetConnectionFromPool(DataSourceTypeEnum dsEnum) throws SQLException {

        DataSource dataSource = getDataSourceFromConnPool(dsEnum);

        String alias = "";

        if (dataSource instanceof ProxoolDataSource) {
            alias = ((ProxoolDataSource) dataSource).getAlias();
        }

        if (dataSource != null) {
            logger.info("dynamic datasource getConnection from pool success, [tenantId] {}," +
                    "[dsType]:{} ,[alias]:{}", tenantId, DataSourceTypeEnum.POOL_MASTER, alias);
        } else {

            logger.info("dynamic datasource getConnection from pool failed, dataSource is null! [tenantId] {}," +
                    "[dsType]:{} ,[alias]:{}", tenantId, DataSourceTypeEnum.POOL_MASTER, alias);
        }

        return dataSource == null ? null : dataSource.getConnection();
    }

    /**
     * 获取数据源
     *
     * @param dsEnum
     * @return
     */
    public DataSource getDataSourceFromConnPool(DataSourceTypeEnum dsEnum) {

        Assert.notNull(dsEnum, "dsEnum cannot be null");

        if (masterDataSource == null) {
            lazyInitPoolSource();
        }

        Assert.notNull(masterDataSource, "masterDataSource cannot be null");

        return masterDataSource.getDataSource();
    }

    /**
     * 获取所有的别名
     *
     * @return
     */
    public List<String> getAllAliasNames() {

        List<String> list = new ArrayList<>();

        if (masterDataSource != null) {
            list.add(masterDataSource.getAliasName());
        }

        return list;
    }

    /**
     * 清理并摧毁数据源连接池
     */
    public void removeAndDestroy() {

        logger.info("******************************************");

        logger.info("begin to remove tenant datasource,tenantId={}", tenantId);

        if (masterDataSource != null) {
            // 清理掉master
            removeConnectionFromPool(masterDataSource);
            logger.info("finish remove tenant master datasource,tenantId={}", tenantId);
        }


        logger.info("end to remove tenant datasource,tenantId={}", tenantId);
        logger.info("******************************************");
    }

    /**
     * 从链接池里彻底删除
     *
     * @param dsUnit
     */
    private void removeConnectionFromPool(DataSourceUnit dsUnit) {

        try {
            ProxoolFacade.removeConnectionPool(dsUnit.getAliasName());
            logger.info("removeConnectionFromPool success![dsUnit]:{}", dsUnit.getAliasName());
        } catch (Exception e) {
            logger.error("removeConnectionFromPool failed![dsUnit]:{}", dsUnit.getAliasName(), e);
        }
    }

    /**
     * 是否使用连接池
     *
     * @return
     */
    public boolean isTenantUseConnPool() {
        return dbInfo.isUseConnPool();
    }

    /**
     * 懒加载 数据库连接池信息
     */
    private void lazyInitPoolSource() {

        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(30, TimeUnit.SECONDS);
            if (isLocked && masterDataSource == null) {
                DataSourcePoolInfo dataSourcePoolInfo = RegisterTenantDatasource.lazyCreateDataSourcePoolInfo(dbInfo);
                this.masterDataSource = dataSourcePoolInfo.getMasterDataSource();
            }

        } catch (Exception e) {
            logger.error("lazyInitPoolSource failed,[tenantId]:{}", tenantId, e);
        } finally {

            if (isLocked) {
                lock.unlock();
            }
        }
    }

}