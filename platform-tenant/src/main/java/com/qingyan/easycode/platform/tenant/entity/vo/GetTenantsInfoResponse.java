package com.qingyan.easycode.platform.tenant.entity.vo;

import java.util.List;

import com.qingyan.easycode.platform.tenant.entity.TenantInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xuzhou
 * @since 2022/11/17
 */
@Setter
@Getter
public class GetTenantsInfoResponse {

    private String resultCode;

    private String resultMessage;

    private Long tenantId;

    private List<TenantInfo> tenantInfoList;

}
