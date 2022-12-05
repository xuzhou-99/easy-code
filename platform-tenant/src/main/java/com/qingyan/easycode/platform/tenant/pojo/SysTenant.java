package com.qingyan.easycode.platform.tenant.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 系统租户信息表
 *
 * @author xuzhou
 * @TableName sys_tenant
 * @since 2022-12-05
 */
@TableName("sys_tenant")
public class SysTenant implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 租户id
     */
    private Long id;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 租户类型
     */
    private String tenantType;
    /**
     * 租户编码
     */
    private String code;
    /**
     * 租户名称
     */
    private String name;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 负责人性别
     */
    private String sex;
    /**
     * 联系人手机号
     */
    private String phone;
    /**
     * 联系人fax
     */
    private String fax;
    /**
     * 联系人手机号
     */
    private String mobile;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 联系邮箱
     */
    private String email;
    /**
     * 地区
     */
    private String region;
    /**
     * 注册地址
     */
    private String regiterOrigin;
    /**
     * 企业
     */
    private String industry;
    /**
     *
     */
    private String url;
    /**
     * 城市
     */
    private String city;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 法人
     */
    private String agentMan;
    /**
     *
     */
    private Long svrId;
    /**
     * 是否使用连接池
     */
    private String isDbpool;
    /**
     * 是否使用从库连接池
     */
    private String isDbpoolSlaver;
    /**
     *
     */
    private Integer minConnCountSlaver;
    /**
     *
     */
    private Integer maxConnCountSlaver;
    /**
     * 用户数量
     */
    private Integer users;
    /**
     * 租户简称
     */
    private String shortName;
    /**
     *
     */
    private String clientAcl;
    /**
     *
     */
    private Long channelId;
    /**
     *
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
    /**
     *
     */
    private String customerManagerId;
    /**
     * 最大用户数量
     */
    private Long maxUser;
    /**
     * 最大存储
     */
    private Long maxStorageSize;
    /**
     *
     */
    private Date storeEndDate;
    /**
     * 租用开始日期
     */
    private Date storeStartDate;
    /**
     * 租用变化描述
     */
    private String storeChangeDesc;
    /**
     *
     */
    private Integer flowNumber;
    /**
     * 租用结束日期
     */
    private Date flowStartDate;
    /**
     *
     */
    private Date flowEndDate;
    /**
     *
     */
    private String flowChangeDesc;
    /**
     * 是否激活
     */
    private String isActive;
    /**
     * 停用原因
     */
    private String stopReason;
    /**
     *
     */
    private String stopRemarks;

    /**
     * 租户id
     */
    public Long getId() {
        return id;
    }

    /**
     * 租户id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 租户类型
     */
    public String getTenantType() {
        return tenantType;
    }

    /**
     * 租户类型
     */
    public void setTenantType(String tenantType) {
        this.tenantType = tenantType;
    }

    /**
     * 租户编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 租户编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 租户名称
     */
    public String getName() {
        return name;
    }

    /**
     * 租户名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 联系人
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * 联系人
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    /**
     * 负责人性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 负责人性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 联系人手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 联系人手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 联系人fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * 联系人fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 联系人手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 联系人手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 联系地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 联系地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 联系邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 联系邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 地区
     */
    public String getRegion() {
        return region;
    }

    /**
     * 地区
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 注册地址
     */
    public String getRegiterOrigin() {
        return regiterOrigin;
    }

    /**
     * 注册地址
     */
    public void setRegiterOrigin(String regiterOrigin) {
        this.regiterOrigin = regiterOrigin;
    }

    /**
     * 企业
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 企业
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     *
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 法人
     */
    public String getAgentMan() {
        return agentMan;
    }

    /**
     * 法人
     */
    public void setAgentMan(String agentMan) {
        this.agentMan = agentMan;
    }

    /**
     *
     */
    public Long getSvrId() {
        return svrId;
    }

    /**
     *
     */
    public void setSvrId(Long svrId) {
        this.svrId = svrId;
    }

    /**
     * 是否使用连接池
     */
    public String getIsDbpool() {
        return isDbpool;
    }

    /**
     * 是否使用连接池
     */
    public void setIsDbpool(String isDbpool) {
        this.isDbpool = isDbpool;
    }

    /**
     * 是否使用从库连接池
     */
    public String getIsDbpoolSlaver() {
        return isDbpoolSlaver;
    }

    /**
     * 是否使用从库连接池
     */
    public void setIsDbpoolSlaver(String isDbpoolSlaver) {
        this.isDbpoolSlaver = isDbpoolSlaver;
    }

    /**
     *
     */
    public Integer getMinConnCountSlaver() {
        return minConnCountSlaver;
    }

    /**
     *
     */
    public void setMinConnCountSlaver(Integer minConnCountSlaver) {
        this.minConnCountSlaver = minConnCountSlaver;
    }

    /**
     *
     */
    public Integer getMaxConnCountSlaver() {
        return maxConnCountSlaver;
    }

    /**
     *
     */
    public void setMaxConnCountSlaver(Integer maxConnCountSlaver) {
        this.maxConnCountSlaver = maxConnCountSlaver;
    }

    /**
     * 用户数量
     */
    public Integer getUsers() {
        return users;
    }

    /**
     * 用户数量
     */
    public void setUsers(Integer users) {
        this.users = users;
    }

    /**
     * 租户简称
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 租户简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     *
     */
    public String getClientAcl() {
        return clientAcl;
    }

    /**
     *
     */
    public void setClientAcl(String clientAcl) {
        this.clientAcl = clientAcl;
    }

    /**
     *
     */
    public Long getChannelId() {
        return channelId;
    }

    /**
     *
     */
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    /**
     *
     */
    public String getPushEnable() {
        return pushEnable;
    }

    /**
     *
     */
    public void setPushEnable(String pushEnable) {
        this.pushEnable = pushEnable;
    }

    /**
     * 主动定位
     */
    public String getLocationPolicyA() {
        return locationPolicyA;
    }

    /**
     * 主动定位
     */
    public void setLocationPolicyA(String locationPolicyA) {
        this.locationPolicyA = locationPolicyA;
    }

    /**
     * 被动定位
     */
    public String getLocationPolicyB() {
        return locationPolicyB;
    }

    /**
     * 被动定位
     */
    public void setLocationPolicyB(String locationPolicyB) {
        this.locationPolicyB = locationPolicyB;
    }

    /**
     *
     */
    public String getCustomerManagerId() {
        return customerManagerId;
    }

    /**
     *
     */
    public void setCustomerManagerId(String customerManagerId) {
        this.customerManagerId = customerManagerId;
    }

    /**
     * 最大用户数量
     */
    public Long getMaxUser() {
        return maxUser;
    }

    /**
     * 最大用户数量
     */
    public void setMaxUser(Long maxUser) {
        this.maxUser = maxUser;
    }

    /**
     * 最大存储
     */
    public Long getMaxStorageSize() {
        return maxStorageSize;
    }

    /**
     * 最大存储
     */
    public void setMaxStorageSize(Long maxStorageSize) {
        this.maxStorageSize = maxStorageSize;
    }

    /**
     *
     */
    public Date getStoreEndDate() {
        return storeEndDate;
    }

    /**
     *
     */
    public void setStoreEndDate(Date storeEndDate) {
        this.storeEndDate = storeEndDate;
    }

    /**
     * 租用开始日期
     */
    public Date getStoreStartDate() {
        return storeStartDate;
    }

    /**
     * 租用开始日期
     */
    public void setStoreStartDate(Date storeStartDate) {
        this.storeStartDate = storeStartDate;
    }

    /**
     * 租用变化描述
     */
    public String getStoreChangeDesc() {
        return storeChangeDesc;
    }

    /**
     * 租用变化描述
     */
    public void setStoreChangeDesc(String storeChangeDesc) {
        this.storeChangeDesc = storeChangeDesc;
    }

    /**
     *
     */
    public Integer getFlowNumber() {
        return flowNumber;
    }

    /**
     *
     */
    public void setFlowNumber(Integer flowNumber) {
        this.flowNumber = flowNumber;
    }

    /**
     * 租用结束日期
     */
    public Date getFlowStartDate() {
        return flowStartDate;
    }

    /**
     * 租用结束日期
     */
    public void setFlowStartDate(Date flowStartDate) {
        this.flowStartDate = flowStartDate;
    }

    /**
     *
     */
    public Date getFlowEndDate() {
        return flowEndDate;
    }

    /**
     *
     */
    public void setFlowEndDate(Date flowEndDate) {
        this.flowEndDate = flowEndDate;
    }

    /**
     *
     */
    public String getFlowChangeDesc() {
        return flowChangeDesc;
    }

    /**
     *
     */
    public void setFlowChangeDesc(String flowChangeDesc) {
        this.flowChangeDesc = flowChangeDesc;
    }

    /**
     * 是否激活
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * 是否激活
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /**
     * 停用原因
     */
    public String getStopReason() {
        return stopReason;
    }

    /**
     * 停用原因
     */
    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

    /**
     *
     */
    public String getStopRemarks() {
        return stopRemarks;
    }

    /**
     *
     */
    public void setStopRemarks(String stopRemarks) {
        this.stopRemarks = stopRemarks;
    }
}