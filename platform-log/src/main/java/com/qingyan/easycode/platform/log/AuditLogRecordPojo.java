package com.qingyan.easycode.platform.log;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * AuditLogRecord
 * 审计日志信息
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/2/10 16:17
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@TableName(value = "sys_audit_log")
public class AuditLogRecordPojo implements Serializable {

    /**
     * 日志编号
     */
    @TableId
    private String bh;

    /**
     * 日志内容
     */
    private String description;

    /**
     * 操作类型
     */
    private Integer operationType;

    /**
     * 操作类型名称
     */
    private String operationName;

    /**
     * 日志模块类型
     */
    private Integer moduleCode;

    /**
     * 日志模块名称
     */
    private String moduleName;

    /**
     * 操作日志时间
     */
    private LocalDateTime operationTime;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户登录名
     */
    private String userLoginName;

    /**
     * userAgent
     */
    private String userAgent;

    /**
     * IP
     */
    private String ip;

    /**
     * 拓展字段
     */
    private String extension;


}
