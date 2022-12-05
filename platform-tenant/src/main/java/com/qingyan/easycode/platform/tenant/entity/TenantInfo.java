package com.qingyan.easycode.platform.tenant.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * 企业信息
 *
 * @author xuzhou
 */
@Setter
@Getter
public class TenantInfo implements Serializable {


    /**
     * 企业数据源信息
     */
    private DbInfo dbInfo;
    /**
     * 实体标识.
     */
    private Long id;
    /**
     * status 0:停用 1:启用 2:禁用、销户
     */
    private String status;
    /**
     * create_time
     */
    private Date createTime;
    /**
     * code
     */
    private String code;
    /**
     * name
     */
    private String name;
    /**
     * linkman
     */
    private String linkman;
    /**
     * phone
     */
    private String phone;
    /**
     * fax
     */
    private String fax;
    /**
     * mobile
     */
    private String mobile;
    /**
     * address
     */
    private String address;
    /**
     * email
     */
    private String email;
    /**
     * url
     */
    private String url;
    /**
     * remarks
     */
    private String remarks;
    /**
     * svr_id
     */
    private Long svrId;
    /**
     * users 通过企业系统管理开通的用户数
     */
    private Long users;
    /**
     * stop_remarks 停用原因
     */
    private String stopRemarks;
    /**
     * 企业简称
     */
    private String shortName;
    /**
     * 访问限制
     */
    private String clientAcl;
    /**
     * 地区
     */
    private String region;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 联系人性别
     */
    private String sex;
    /**
     * 运营商类型
     */
    private String networkType;
    /**
     * 渠道门户logo路径
     */
    private String portalUrl;
    /**
     * PUSH开通状态,true开通，false不开通
     */
    private String pushEnable;
    /**
     * 主动定位
     */
    private String locationPolicyA;
    /**
     * 被动定位
     */
    private String locationPolicyB;
    private String maxStorageSize;
    private String dbSize;
    private String fileSize;
    /**
     * 注册类型
     */
    private String registerOrigin;
    /**
     * 客户类型
     * {@link com.qingyan.easycode.platform.tenant.enums.TenantTypeEnum}
     */
    private String tenantType;
    /**
     * 数据库信息Id
     */
    private Long dbId;
}