package com.qingyan.easycode.platform.tenant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingyan.easycode.platform.tenant.pojo.SysTenant;

/**
 * @author xuzhou
 * @since 2022/11/17
 */
@Mapper
public interface TenantMapper extends BaseMapper<SysTenant> {
}
