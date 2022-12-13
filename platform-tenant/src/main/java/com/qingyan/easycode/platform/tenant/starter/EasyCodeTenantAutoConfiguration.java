package com.qingyan.easycode.platform.tenant.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qingyan.easycode.platform.tenant.redis.RedisUtil;
import com.qingyan.easycode.platform.tenant.service.impl.GetTenantInfoImpl;
import com.qingyan.easycode.platform.tenant.service.IGetTenantInfo;

/**
 * @author xuzhou
 * @since 2022/11/15
 */
@MapperScan("com.qingyan.easycode.platform.tenant.mapper")
@Configuration
public class EasyCodeTenantAutoConfiguration {


    /**
     * 系统日志 service
     */
    @Bean
    @ConditionalOnMissingBean(IGetTenantInfo.class)
    public IGetTenantInfo getTenantInfo() {
        return new GetTenantInfoImpl();
    }

    /**
     * 系统日志 service
     */
    @Bean
    @ConditionalOnMissingBean(RedisUtil.class)
    public RedisUtil redisUtil() {
        return new RedisUtil();
    }

}
