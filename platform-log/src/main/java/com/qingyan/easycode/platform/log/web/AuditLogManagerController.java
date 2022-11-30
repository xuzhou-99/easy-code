/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
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
 * @date 2020/11/3 12:44
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
     */
    @GetMapping(name = "查询日志列表", path = "/logManager/list")
    public ApiDataResponse<List<AuditLogRecordPojo>> list(@RequestBody LogManagerRequest logManagerRequest) {
        List<AuditLogRecordPojo> list = auditLogHandler.findList(logManagerRequest);
        return ApiDataResponse.ofSuccess(list);
    }

    /**
     * 查询日志
     */
    @GetMapping(name = "查询日志列表", path = "/logManager/page")
    public ApiDataResponse<Page<AuditLogRecordPojo>> page(LogManagerRequest logManagerRequest) {
        Page<AuditLogRecordPojo> result = auditLogHandler.findPage(logManagerRequest);
        return ApiDataResponse.ofSuccess(result);
    }

    /**
     * 删除日志
     */
    @PostMapping(name = "删除日志", path = "/logManager/delete")
    @AuditLog(description = "删除日志", module = AuditLogModuleEnum.DATA, operationType = AuditLogOperationTypeEnum.delete)
    public ApiResponse delete(@RequestBody LogManagerRequest logManagerRequest) {
        auditLogHandler.delAll(logManagerRequest);
        return ApiResponse.ofSuccess();
    }

    /**
     * 查看日志详情
     */
    @GetMapping(name = "查看日志详情", path = "/logManager/detail")
    public ApiDataResponse<AuditLogRecordPojo> detail(LogManagerRequest logManagerRequest) {
        AuditLogRecordPojo auditLogRecord = auditLogHandler.detail(logManagerRequest);
        return ApiDataResponse.ofSuccess(auditLogRecord);
    }

}
