package com.qingyan.easycode.platform.log.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyan.easycode.platform.log.AuditLogRecordPojo;
import com.qingyan.easycode.platform.log.service.AuditLogHandler;
import com.qingyan.easycode.platform.log.web.vo.request.LogManagerRequest;

import cn.altaria.audit.annotation.AuditLog;
import cn.altaria.audit.enums.AuditLogModuleEnum;
import cn.altaria.audit.enums.AuditLogOperationTypeEnum;
import cn.altaria.base.response.ApiDataResponse;
import cn.altaria.base.response.ApiResponse;

/**
 * 日志管理控制器
 *
 * @author xuzhou
 * @version v1.0.0
 * @since 2022/2/10 16:17
 */
@RestController
@RequestMapping(name = "日志管理控制器")
public class AuditLogManagerController {

    /**
     * 日志管理service
     */
    @Resource
    private AuditLogHandler auditLogHandler;

    /**
     * 查询日志列表
     *
     * @param logManagerRequest 日志查询参数
     * @return 日志列表
     */
    @GetMapping(name = "查询日志列表", path = "/logManager/list")
    public ApiDataResponse<List<AuditLogRecordPojo>> list(@RequestBody LogManagerRequest logManagerRequest) {
        List<AuditLogRecordPojo> list = auditLogHandler.findList(logManagerRequest);
        return ApiDataResponse.ofSuccess(list);
    }

    /**
     * 查询日志-分页
     *
     * @param logManagerRequest 日志查询参数
     * @return 日志列表
     */
    @GetMapping(name = "查询日志列表", path = "/logManager/page")
    public ApiDataResponse<Page<AuditLogRecordPojo>> page(LogManagerRequest logManagerRequest) {
        Page<AuditLogRecordPojo> result = auditLogHandler.findPage(logManagerRequest);
        return ApiDataResponse.ofSuccess(result);
    }

    /**
     * 删除日志
     *
     * @param logManagerRequest 日志删除参数
     * @return 操作结果
     */
    @PostMapping(name = "删除日志", path = "/logManager/delete")
    @AuditLog(description = "删除日志", module = AuditLogModuleEnum.DATA, operationType = AuditLogOperationTypeEnum.delete)
    public ApiResponse delete(@RequestBody LogManagerRequest logManagerRequest) {
        auditLogHandler.delAll(logManagerRequest);
        return ApiResponse.ofSuccess();
    }

    /**
     * 查看日志详情
     *
     * @param logManagerRequest 日志查询参数
     * @return 日志详情
     */
    @GetMapping(name = "查看日志详情", path = "/logManager/detail")
    public ApiDataResponse<AuditLogRecordPojo> detail(LogManagerRequest logManagerRequest) {
        AuditLogRecordPojo auditLogRecord = auditLogHandler.detail(logManagerRequest);
        return ApiDataResponse.ofSuccess(auditLogRecord);
    }

}
