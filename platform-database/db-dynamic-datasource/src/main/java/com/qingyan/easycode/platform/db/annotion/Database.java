package com.qingyan.easycode.platform.db.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qingyan.easycode.platform.db.constans.DataSourceTypeEnum;


/**
 * @author xuzhou
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Database {


    /**
     * 设置数据源类型:主库，从库，还是统计库
     *
     * @return
     */
    DataSourceTypeEnum dataSourceType() default DataSourceTypeEnum.POOL_MASTER;

    /**
     * 指定数据源,可以配合@Transactional一起使用.
     *
     * @return
     */
    String dataSource() default "";

}
