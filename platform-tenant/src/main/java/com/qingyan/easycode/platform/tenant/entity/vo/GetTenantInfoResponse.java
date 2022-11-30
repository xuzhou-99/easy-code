package com.qingyan.easycode.platform.tenant.entity.vo;

import com.qingyan.easycode.platform.tenant.entity.TenantInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xuzhou
 * @since 2022/11/17
 */
@Setter
@Getter
public class GetTenantInfoResponse {

    private String resultCode;

    private String resultMessage;

    private Long tenantId;

    private TenantInfo tenantInfo;

}
