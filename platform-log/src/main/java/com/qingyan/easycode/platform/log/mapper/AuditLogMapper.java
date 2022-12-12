package com.qingyan.easycode.platform.log.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingyan.easycode.platform.log.AuditLogRecordPojo;

/**
 * @author xuzhou
 * @version v1.0.0
 * @since 2022/11/15
 */
@Mapper
public interface AuditLogMapper extends BaseMapper<AuditLogRecordPojo> {
}
