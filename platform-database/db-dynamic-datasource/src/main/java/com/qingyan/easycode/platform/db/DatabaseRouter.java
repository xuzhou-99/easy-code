package com.qingyan.easycode.platform.db;

import org.springframework.util.Assert;

import com.qingyan.easycode.platform.db.annotion.Database;
import com.qingyan.easycode.platform.db.constans.DataSourceTypeEnum;


/**
 * 主从数据源
 *
 * @author xuzhou
 * @since 2022-10-10
 */
public class DatabaseRouter {

    private static final ExtendAnnotationThreadLocal<DataSourceTypeEnum> DS_TYPE_LOCAL = new ExtendAnnotationThreadLocal<>();


    private DatabaseRouter() {

    }


    /**
     * 设置是否统计库的标志
     *
     * @param database 注解
     */
    @SuppressWarnings("deprecation")
    public static void setDataSourceType(Database database) {

        DataSourceTypeEnum type = database.dataSourceType();
        Assert.notNull(type);
        DS_TYPE_LOCAL.set(type);
    }


    /**
     * 设置数据源类型
     *
     * @param databaseType 数据源类型
     */
    public static void setDataSourceType(DataSourceTypeEnum databaseType) {

        Assert.notNull(databaseType, "数据源类型不能为空！");
        DS_TYPE_LOCAL.set(databaseType);
    }


    /**
     * 清理本层的
     */
    public static void removeOne() {
        DS_TYPE_LOCAL.removeOne();
    }


    /**
     * 清理所有的
     */
    public static void removeAll() {
        DS_TYPE_LOCAL.remove();
    }


    /**
     * 获取ds type local 变量
     *
     * @return 数据源类型
     */
    public static DataSourceTypeEnum getDsTypeLocal() {
        return DS_TYPE_LOCAL.get();
    }

    /**
     * 决定当前的数据源类型
     *
     * @return 数据源类型
     */
    public static DataSourceTypeEnum determineDataSourceType() {

        DataSourceTypeEnum val = DS_TYPE_LOCAL.get();

        return val == null ? DataSourceTypeEnum.POOL_MASTER : val;
    }


}
