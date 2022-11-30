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

    private static final ExtendAnnotationThreadLocal<DataSourceTypeEnum> dsTypeLocal = new ExtendAnnotationThreadLocal<>();


    private DatabaseRouter() {

    }


    /**
     * 设置是否统计库的标志
     *
     * @param database 注解
     */
    @SuppressWarnings("deprecation")
    public static void setDataSourceType(Database database) {

        DataSourceTypeEnum type = database.DataSourceType();
        Assert.notNull(type);
        dsTypeLocal.set(type);
    }


    /**
     * 设置数据源类型
     *
     * @param databaseType 数据源类型
     */
    public static void setDataSourceType(DataSourceTypeEnum databaseType) {

        Assert.notNull(databaseType, "数据源类型不能为空！");
        dsTypeLocal.set(databaseType);
    }


    /**
     * 清理本层的
     */
    public static void removeOne() {
        dsTypeLocal.removeOne();
    }


    /**
     * 清理所有的
     */
    public static void removeAll() {
        dsTypeLocal.remove();
    }


    /**
     * 获取ds type local 变量
     *
     * @return 数据源类型
     */
    public static DataSourceTypeEnum getDsTypeLocal() {
        return dsTypeLocal.get();
    }

    /**
     * 决定当前的数据源类型
     *
     * @return 数据源类型
     */
    public static DataSourceTypeEnum determineDataSourceType() {

        DataSourceTypeEnum val = dsTypeLocal.get();

        return val == null ? DataSourceTypeEnum.POOL_MASTER : val;
    }


}
