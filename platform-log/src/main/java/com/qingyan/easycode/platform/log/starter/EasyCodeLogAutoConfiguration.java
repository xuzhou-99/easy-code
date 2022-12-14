package com.qingyan.easycode.platform.log.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qingyan.easycode.platform.log.service.AuditLogHandler;
import com.qingyan.easycode.platform.log.web.AuditLogManagerController;

import cn.altaria.audit.handler.AbstractAuditLogHandler;

/**
 * @author xuzhou
 * @version v1.0.0
 * @since 2022/11/15
 */
@MapperScan("com.qingyan.easycode.platform.log.mapper")
@Configuration
public class EasyCodeLogAutoConfiguration {


    /**
     * 系统日志 service
     */
    @Bean
    @ConditionalOnMissingBean(AbstractAuditLogHandler.class)
    public AbstractAuditLogHandler auditLogHandler() {
        return new AuditLogHandler();
    }

    /**
     * 系统日志 Controller
     */
    @Bean
    @ConditionalOnMissingBean(AuditLogManagerController.class)
    public AuditLogManagerController auditLogManagerController() {
        return new AuditLogManagerController();
    }


}
