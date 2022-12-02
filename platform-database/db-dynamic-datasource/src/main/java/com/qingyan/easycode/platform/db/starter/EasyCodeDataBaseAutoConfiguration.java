package com.qingyan.easycode.platform.db.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qingyan.easycode.platform.db.connect.JdbcConnectionManager;
import com.qingyan.easycode.platform.db.filter.DynamicThreadLocalInterceptor;
import com.qingyan.easycode.platform.db.register.DynamicRegister;

/**
 * @author xuzhou
 * @since 2022/11/15
 */
@Configuration
public class EasyCodeDataBaseAutoConfiguration {


    /**
     * 系统日志 service
     */
    @Bean
    public DynamicRegister dynamicRegister() {
        return new DynamicRegister();
    }

    /**
     * 系统日志 service
     */
    @Bean
    public JdbcConnectionManager jdbcConnectionManager() {
        return new JdbcConnectionManager();
    }

    @Bean
    public DynamicThreadLocalInterceptor threadLocalInterceptor() {
        return new DynamicThreadLocalInterceptor();
    }

}
