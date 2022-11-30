package com.qingyan.easycode.platform.log.web.vo.request;

import lombok.Data;

/**
 * 日志管理的查询参数
 *
 * @author fengshuonan
 * @date 2020/10/28 11:26
 */
@Data
public class LogManagerRequest {

    /**
     * 单条日志id
     */
    private Long logId;

    /**
     * 查询的起始时间
     */
    private String beginDate;

    /**
     * 查询日志的结束时间
     */
    private String endDate;

    /**
     * 日志的名称，一般为业务名称
     */
    private String logName;

    /**
     * 服务名称，一般为spring.application.name
     */
    private String appName;

    /**
     * 当前服务器的ip
     */
    private String serverIp;

    /**
     * 客户端请求的用户id
     */
    private Long userId;

    /**
     * 客户端的ip
     */
    private String clientIp;

    /**
     * 当前用户请求的url
     */
    private String requestUrl;

}
