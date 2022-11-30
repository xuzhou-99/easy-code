package com.qingyan.easycode.platform.tenant.service;

import com.qingyan.easycode.platform.tenant.entity.vo.GetAllTenantIdResponse;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantInfoRequest;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantInfoResponse;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantsInfoRequest;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantsInfoResponse;

/**
 * @author xuzhou
 * @since 2022/11/17
 */
public interface IGetTenantInfo {
    /**
     * 根据企业Id查询企业信息
     *
     * @param request 企业信息查询参数
     * @return 企业信息
     */
    GetTenantInfoResponse handleGetTenantInfo(GetTenantInfoRequest request);

    /**
     * 根据企业编码查询企业信息
     *
     * @param request 企业信息查询参数
     * @return 企业信息多个
     */
    GetTenantsInfoResponse handleGetTenantsInfo(GetTenantsInfoRequest request);

    /**
     * 获取所有企业信息
     *
     * @return 所有企业信息
     */
    GetAllTenantIdResponse handleGetAllTenantId();

    /**
     * 根据企业编码查询企业信息
     *
     * @param request 企业信息查询参数
     * @return 企业信息多个
     */
    GetTenantInfoResponse handleGetTenantByCode(GetTenantsInfoRequest request);
}
