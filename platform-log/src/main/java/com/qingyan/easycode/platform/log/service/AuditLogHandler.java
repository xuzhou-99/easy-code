package com.qingyan.easycode.platform.log.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.logging.LogException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyan.easycode.platform.log.AuditLogRecordPojo;
import com.qingyan.easycode.platform.log.enums.LogExceptionEnum;
import com.qingyan.easycode.platform.log.mapper.AuditLogMapper;
import com.qingyan.easycode.platform.log.web.vo.request.LogManagerRequest;

import cn.altaria.audit.handler.IAuditLogHandler;
import cn.altaria.audit.pojo.AuditLogRecord;
import cn.altaria.base.util.RequestUtils;

/**
 * @author xuzhou
 * @since 2022/11/15
 */
@Service
public class AuditLogHandler implements IAuditLogHandler {

    @Resource
    private AuditLogMapper auditLogMapper;

    /**
     * 插入审计日志
     *
     * @param auditLogRecord {@link AuditLogRecord}
     */
    @Override
    public void handleRecord(AuditLogRecord auditLogRecord) {

        // 获取 RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null != requestAttributes) {
            // 获取 HttpServletRequest
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            // 获取IP
            String ip = RequestUtils.getIpAddress(request);
            auditLogRecord.setIp(ip);

            // 获取 user-agent
            String userAgent = RequestUtils.getUserAgent(request);
            auditLogRecord.setUserAgent(userAgent);
        }

        AuditLogRecordPojo auditLogRecordPojo = new AuditLogRecordPojo();
        BeanUtils.copyProperties(auditLogRecord, auditLogRecordPojo);
        auditLogMapper.insert(auditLogRecordPojo);
    }


    public void del(LogManagerRequest logManagerRequest) {
        AuditLogRecordPojo logRecord = this.queryAuditLogRecordById(logManagerRequest);
        auditLogMapper.deleteById(logRecord.getBh());
    }


    public void delAll(LogManagerRequest logManagerRequest) {
        LambdaUpdateWrapper<AuditLogRecordPojo> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.between(AuditLogRecordPojo::getOperationTime, logManagerRequest.getBeginDate() + " 00:00:00", logManagerRequest.getEndDate() + " 23:59:59");
        queryWrapper.eq(AuditLogRecordPojo::getModuleName, logManagerRequest.getAppName());
        auditLogMapper.delete(queryWrapper);
    }


    public AuditLogRecordPojo detail(LogManagerRequest logManagerRequest) {
        LambdaQueryWrapper<AuditLogRecordPojo> queryWrapper = this.createWrapper(logManagerRequest);
        return auditLogMapper.selectOne(queryWrapper);
    }


    public List<AuditLogRecordPojo> findList(LogManagerRequest logManagerRequest) {
        LambdaQueryWrapper<AuditLogRecordPojo> wrapper = this.createWrapper(logManagerRequest);
        return auditLogMapper.selectList(wrapper);
    }


    public Page<AuditLogRecordPojo> findPage(LogManagerRequest logManagerRequest) {
        LambdaQueryWrapper<AuditLogRecordPojo> wrapper = createWrapper(logManagerRequest);
        Page<AuditLogRecordPojo> page = new Page<>();
        page.setCurrent(0).setSize(10);
        page = auditLogMapper.selectPage(page, wrapper);
        return page;
    }

    /**
     * 根据主键id获取对象
     */
    private AuditLogRecordPojo queryAuditLogRecordById(LogManagerRequest logManagerRequest) {
        AuditLogRecordPojo logRecord = auditLogMapper.selectById(logManagerRequest.getLogId());
        if (logRecord == null) {
            throw new LogException(String.format(LogExceptionEnum.LOG_NOT_EXISTED.getUserTip(), logManagerRequest.getLogId()));
        }
        return logRecord;
    }

    /**
     * 实体构建queryWrapper
     */
    private LambdaQueryWrapper<AuditLogRecordPojo> createWrapper(LogManagerRequest logManagerRequest) {
        LambdaQueryWrapper<AuditLogRecordPojo> queryWrapper = new LambdaQueryWrapper<>();

        // 根据时间倒序排序
        queryWrapper.orderByDesc(AuditLogRecordPojo::getOperationTime);

        if (ObjectUtils.isEmpty(logManagerRequest)) {
            return queryWrapper;
        }

        String beginDateTime = logManagerRequest.getBeginDate();
        String endDateTime = logManagerRequest.getEndDate();

        Date beginDate = null;
        Date endDate = null;
        try {
            if (StringUtils.isNotBlank(beginDateTime)) {
                beginDate = DateUtils.parseDate(beginDateTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            }
            if (StringUtils.isNotBlank(endDateTime)) {
                endDate = DateUtils.parseDate(endDateTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            }
        } catch (Exception e) {
            throw new LogException("日期转换异常...");
        }


        // SQL条件拼接
        String name = logManagerRequest.getLogName();
        String appName = logManagerRequest.getAppName();
        Long userId = logManagerRequest.getUserId();
        String clientIp = logManagerRequest.getClientIp();
        Long logId = logManagerRequest.getLogId();

        queryWrapper.eq(logId != null, AuditLogRecordPojo::getBh, logId);
        queryWrapper.between(org.apache.commons.lang3.ObjectUtils.allNotNull(beginDate, endDate), AuditLogRecordPojo::getOperationTime, beginDate, endDate);
        queryWrapper.like(StringUtils.isNotEmpty(name), AuditLogRecordPojo::getModuleName, name);
        queryWrapper.like(StringUtils.isNotEmpty(appName), AuditLogRecordPojo::getModuleName, appName);
        queryWrapper.eq(userId != null, AuditLogRecordPojo::getUserId, userId);
        queryWrapper.eq(StringUtils.isNotEmpty(clientIp), AuditLogRecordPojo::getIp, clientIp);

        return queryWrapper;
    }

}
