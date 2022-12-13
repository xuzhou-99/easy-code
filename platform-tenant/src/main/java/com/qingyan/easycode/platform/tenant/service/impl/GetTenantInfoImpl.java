package com.qingyan.easycode.platform.tenant.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingyan.easycode.platform.tenant.constans.TenantConstants;
import com.qingyan.easycode.platform.tenant.entity.DbInfo;
import com.qingyan.easycode.platform.tenant.entity.TenantInfo;
import com.qingyan.easycode.platform.tenant.entity.vo.GetAllTenantIdResponse;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantInfoRequest;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantInfoResponse;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantsInfoRequest;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantsInfoResponse;
import com.qingyan.easycode.platform.tenant.mapper.TenantDataBaseMapper;
import com.qingyan.easycode.platform.tenant.mapper.TenantMapper;
import com.qingyan.easycode.platform.tenant.pojo.SysDatabaseInfo;
import com.qingyan.easycode.platform.tenant.pojo.SysTenant;
import com.qingyan.easycode.platform.tenant.service.IGetTenantInfo;

/**
 * @author xuzhou
 * @since 2022/11/17
 */
@Service
public class GetTenantInfoImpl implements IGetTenantInfo {

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private TenantDataBaseMapper tenantDataBaseMapper;


    /**
     * 根据企业Id查询企业信息
     *
     * @param request 企业信息查询参数
     * @return 企业信息
     */
    @Override
    public GetTenantInfoResponse handleGetTenantInfo(GetTenantInfoRequest request) {

        Long tenantId = request.getTenantId();
        SysTenant tenant = tenantMapper.selectById(tenantId);
        if (tenant == null) {
            GetTenantInfoResponse response = new GetTenantInfoResponse();
            response.setTenantId(tenantId);
            response.setTenantInfo(null);
            response.setResultCode(TenantConstants.REQUEST_STATUS_NOT_FOUND);
            response.setResultMessage(tenantId + " 企业不存在！");
            return response;
        }
        LambdaQueryWrapper<SysDatabaseInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDatabaseInfo::getTenantId, tenantId);
        SysDatabaseInfo databaseInfo = tenantDataBaseMapper.selectOne(queryWrapper);

        TenantInfo tenantInfo = new TenantInfo();
        BeanUtils.copyProperties(tenant, tenantInfo);
        DbInfo dbInfo = new DbInfo();
        BeanUtils.copyProperties(databaseInfo, dbInfo);
        tenantInfo.setDbInfo(dbInfo);

        GetTenantInfoResponse response = new GetTenantInfoResponse();
        response.setTenantId(tenantId);
        response.setTenantInfo(tenantInfo);
        response.setResultCode(TenantConstants.REQUEST_STATUS_SUCCESS);
        return response;
    }

    /**
     * 根据企业编码查询企业信息
     *
     * @param request 企业信息查询参数
     * @return 企业信息多个
     */
    @Override
    public GetTenantsInfoResponse handleGetTenantsInfo(GetTenantsInfoRequest request) {
        return null;
    }

    /**
     * 获取所有企业信息
     *
     * @return 所有企业信息
     */
    @Override
    public GetAllTenantIdResponse handleGetAllTenantId() {

        LambdaQueryWrapper<SysTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTenant::getStatus, "1");

        List<SysTenant> tenantInfos = tenantMapper.selectList(queryWrapper);
        if (tenantInfos == null || tenantInfos.isEmpty()) {
            GetAllTenantIdResponse response = new GetAllTenantIdResponse();
            response.setTenantIds(Collections.emptyList());
            response.setResultMessage("当前系统无租户信息");
            response.setResultCode(TenantConstants.REQUEST_STATUS_NO_INFO);
            return response;
        }

        List<String> list = tenantInfos.stream().map(o -> String.valueOf(o.getId())).collect(Collectors.toList());

        GetAllTenantIdResponse response = new GetAllTenantIdResponse();
        response.setTenantIds(list);
        response.setResultCode(TenantConstants.REQUEST_STATUS_SUCCESS);
        return response;
    }

    @Override
    public GetTenantInfoResponse handleGetTenantByCode(GetTenantsInfoRequest request) {

        LambdaQueryWrapper<SysTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTenant::getCode, request.getTenantCode());
        SysTenant tenant = tenantMapper.selectOne(queryWrapper);

        if (tenant == null) {
            GetTenantInfoResponse response = new GetTenantInfoResponse();
            response.setTenantId(null);
            response.setTenantInfo(null);
            response.setResultMessage(request.getTenantCode() + " 企业不存在！");
            response.setResultCode(TenantConstants.REQUEST_STATUS_NOT_FOUND);
            return response;
        }

        LambdaQueryWrapper<SysDatabaseInfo> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(SysDatabaseInfo::getTenantId, tenant.getId());
        SysDatabaseInfo databaseInfo = tenantDataBaseMapper.selectOne(queryWrapper1);

        TenantInfo tenantInfo = new TenantInfo();
        BeanUtils.copyProperties(tenant, tenantInfo);
        DbInfo dbInfo = new DbInfo();
        BeanUtils.copyProperties(databaseInfo, dbInfo);
        tenantInfo.setDbInfo(dbInfo);

        GetTenantInfoResponse response = new GetTenantInfoResponse();
        response.setTenantId(tenantInfo.getId());
        response.setTenantInfo(tenantInfo);
        response.setResultCode(TenantConstants.REQUEST_STATUS_SUCCESS);
        return response;
    }
}
