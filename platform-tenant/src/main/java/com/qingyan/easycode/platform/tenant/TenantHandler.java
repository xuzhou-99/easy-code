package com.qingyan.easycode.platform.tenant;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.qingyan.easycode.platform.tenant.constans.TenantConstants;
import com.qingyan.easycode.platform.tenant.entity.TenantInfo;
import com.qingyan.easycode.platform.tenant.entity.vo.GetAllTenantIdResponse;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantInfoRequest;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantInfoResponse;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantsInfoRequest;
import com.qingyan.easycode.platform.tenant.entity.vo.GetTenantsInfoResponse;
import com.qingyan.easycode.platform.tenant.exception.TenantException;
import com.qingyan.easycode.platform.tenant.redis.RedisUtil;
import com.qingyan.easycode.platform.tenant.service.IGetTenantInfo;

import cn.altaria.base.util.SpringBeanUtils;


/**
 * 获取企业信息接口
 *
 * @author xuzhou
 */
public final class TenantHandler {

    /**
     * 企业数据库信息缓存
     */
    public static final String DB_INFO_CACHE_KEY_PREFIX = "tenant:dbInfo:";
    /**
     * 企业信息缓存
     */
    public static final String TENANT_INFO_CACHE_KEY_PREFIX = "tenant:info:";
    public static final int DB_CACHE_HOURS = 48;
    private static final Logger logger = LoggerFactory.getLogger(TenantHandler.class);
    private static RedisUtil redisUtil;


    /**
     * 私有构造函数
     */
    private TenantHandler() {
        logger.info("初始化企业信息远程接口！");
        getStringRedisTemplate();
    }

    public static void getStringRedisTemplate() {
        if (redisUtil == null) {
            redisUtil = SpringBeanUtils.getBean(RedisUtil.class);
        }
    }

    /**
     * 获取远端企业服务
     *
     * @return 企业服务
     */
    private static IGetTenantInfo getTenantInfo() {
        return SpringBeanUtils.getBean(IGetTenantInfo.class);
    }


    /**
     * 获取企业的信息
     *
     * @param tenantId 企业ID
     * @return 企业信息
     */
    public static TenantInfo getTenantInfo(Long tenantId) {

        Assert.notNull(tenantId, "获取JDBC 企业{}数据源失败,原因：tenantId不能为空");
        TenantInfo tenantInfo = doGetTenantFromCache(tenantId);
        if (tenantInfo != null) {
            return tenantInfo;
        }
        try {
            tenantInfo = doGetTenant(tenantId);
            getStringRedisTemplate();
            String cacheKey = getDbInfoCacheKey(tenantId);
            redisUtil.cacheValue(cacheKey, JSON.toJSONString(tenantInfo), DB_CACHE_HOURS, TimeUnit.HOURS);

            return tenantInfo;
        } catch (Exception e) {
            logger.warn("获取JDBC 企业{}数据源失败,原因：{}", tenantId, e.getMessage());
            throw e;
        }
    }

    /**
     * 删除企业 DbInfo 的缓存key,删除数据源时，也要删除这个缓存key
     *
     * @param tenantId 企业Id
     */
    public static void deleteDbInfoCache(long tenantId) {
        try {
            String key = getDbInfoCacheKey(tenantId);
            getStringRedisTemplate();
            redisUtil.removeValue(key);
        } catch (Exception e) {
            logger.error("删除企业 {} 信息缓存失败", tenantId, e);
        }

    }


    /**
     * 缓存中查询企业信息
     *
     * @param tenantId 企业Id
     * @return 企业信息
     */
    private static TenantInfo doGetTenantFromCache(long tenantId) {

        try {
            getStringRedisTemplate();
            String cacheKey = getDbInfoCacheKey(tenantId);
            String val = String.valueOf(redisUtil.getValue(cacheKey));
            if (StringUtils.isEmpty(val)) {
                return null;
            }
            return JSON.parseObject(val, TenantInfo.class);
        } catch (Exception e) {
            logger.warn("redis 获取JDBC 企业 {} 数据源信息失败,原因：{}", tenantId, e.getMessage());
            return null;
        }
    }

    /**
     * 缓存key
     *
     * @param tenantId 企业Id
     * @return key
     */
    private static String getDbInfoCacheKey(long tenantId) {
        return TENANT_INFO_CACHE_KEY_PREFIX + tenantId;
    }

    /**
     * 根据企业ID获取企业信息
     *
     * @param tenantId 企业ID
     * @return MbsTenantBean
     */
    public static TenantInfo doGetTenant(Long tenantId) {

        Assert.notNull(tenantId, "获取企业信息失败，tenantId不能为空");

        GetTenantInfoRequest request = new GetTenantInfoRequest();
        request.setTenantId(tenantId);
        GetTenantInfoResponse response = getTenantInfo().handleGetTenantInfo(request);

        if (TenantConstants.REQUEST_STATUS_SUCCESS.equals(response.getResultCode())) {
            TenantInfo tenantInfo = response.getTenantInfo();
            if (tenantInfo == null) {
                throw new TenantException("没找到企业信息！原因：" + response.getResultMessage());
            }
            return tenantInfo;
        } else {
            throw new TenantException(response.getResultMessage());
        }
    }

    /**
     * 根据企业编码查询企业信息列表
     *
     * @param tenantCode 企业编码
     * @return 企业信息列表
     */
    public static List<TenantInfo> doGetTenants(String tenantCode) {

        Assert.notNull(tenantCode, "获取企业信息失败，tenantCode不能为空");

        GetTenantsInfoRequest request = new GetTenantsInfoRequest();
        request.setTenantCode(tenantCode);
        GetTenantsInfoResponse response = getTenantInfo().handleGetTenantsInfo(request);

        if (TenantConstants.REQUEST_STATUS_SUCCESS.equals(response.getResultCode())) {
            List<TenantInfo> mbsTenantBeanList = response.getTenantInfoList();
            if (mbsTenantBeanList == null || mbsTenantBeanList.isEmpty()) {
                throw new TenantException("没找到企业[" + tenantCode + "]信息！");
            }
            return mbsTenantBeanList;
        } else {
            throw new TenantException(response.getResultMessage());
        }

    }

    /**
     * 根据企业编码查询企业信息列表
     *
     * @param tenantCode 企业编码
     * @return 企业信息列表
     */
    public static TenantInfo doGetTenantByCode(String tenantCode) {

        Assert.notNull(tenantCode, "获取企业信息失败，tenantCode不能为空");

        GetTenantsInfoRequest request = new GetTenantsInfoRequest();
        request.setTenantCode(tenantCode);
        GetTenantInfoResponse response = getTenantInfo().handleGetTenantByCode(request);

        if (TenantConstants.REQUEST_STATUS_SUCCESS.equals(response.getResultCode())) {
            TenantInfo mbsTenantBeanList = response.getTenantInfo();
            if (mbsTenantBeanList == null) {
                throw new TenantException("没找到企业[" + tenantCode + "]信息！");
            }
            return mbsTenantBeanList;
        } else {
            throw new TenantException(response.getResultMessage());
        }

    }

    /**
     * 获取所有的企业Id
     *
     * @return 企业Id集合
     */
    public static List<String> getAllTenantIds() {
        GetAllTenantIdResponse response = getTenantInfo().handleGetAllTenantId();
        if (TenantConstants.REQUEST_STATUS_SUCCESS.equals(response.getResultCode())) {
            return response.getTenantIds();
        } else {
            throw new TenantException(response.getResultMessage());
        }
    }


}
