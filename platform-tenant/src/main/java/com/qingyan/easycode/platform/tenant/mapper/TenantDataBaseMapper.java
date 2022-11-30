package com.qingyan.easycode.platform.tenant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingyan.easycode.platform.tenant.pojo.SysDatabaseInfo;

/**
 * @author xuzhou
 * @since 2022/11/17
 */
@Mapper
public interface TenantDataBaseMapper extends BaseMapper<SysDatabaseInfo> {
}
