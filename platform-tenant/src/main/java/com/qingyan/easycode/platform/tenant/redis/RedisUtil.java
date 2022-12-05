package com.qingyan.easycode.platform.tenant.redis;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author xuzhou
 * @since 2022/11/17
 */
@Component
public class RedisUtil {

    /**
     * slf4j 日志
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加 key:string 缓存
     *
     * @param k    key
     * @param v    value
     * @param time time
     * @return {@link boolean}
     */
    public boolean cacheValue(String k, Object v, long time, TimeUnit timeUnit) {
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            ops.set(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error("缓存存入失败key:[{}] value:[{}]", k, v);
        }
        return false;
    }

    /**
     * 添加 key:string 缓存
     *
     * @param k    key
     * @param v    value
     * @param time time
     * @return {@link boolean}
     */
    public boolean cacheValue(String k, Object v, long time) {
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            ops.set(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("缓存存入失败key:[{}] value:[{}]", k, v);
        }
        return false;
    }


    /**
     * 添加 key:string 缓存
     *
     * @param key   key
     * @param value value
     * @return {@link boolean}
     */
    public boolean cacheValue(String key, Object value) {
        return cacheValue(key, value, -1);
    }

    /**
     * 当key不存在时添加 key:string 缓存(setNX)
     * 当出现异常时会返回{@code null},可以根据实际情况改为false
     *
     * @param k    key
     * @param v    value
     * @param time time
     * @return {@link Boolean}
     */
    public Boolean cacheValueIfAbsent(String k, Object v, long time) {
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            Boolean setFlag = ops.setIfAbsent(k, v);
            if (Boolean.TRUE.equals(setFlag) && time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return setFlag;
        } catch (Exception e) {
            log.error("setNX执行失败key:[{}] value:[{}]", k, v);
        }
        return false;
    }


    /**
     * 当key不存在时添加 key:string 缓存(setNX)
     * 当出现异常时会返回{@code null},可以根据实际情况改为false
     *
     * @param key   key
     * @param value value
     * @return {@link Boolean}
     */
    public Boolean cacheValueIfAbsent(String key, Object value) {
        return cacheValueIfAbsent(key, value, -1);
    }

    /**
     * 查询缓存 key 是否存在
     *
     * @param key key
     * @return true/false
     */
    public boolean containsKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.error("判断缓存存在失败key:[" + key + "],错误信息 [{}]", e);
        }
        return false;
    }


    /**
     * 根据 key 获取缓存value
     *
     * @param key key
     * @return value
     */
    public Object getValue(String key) {
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            return ops.get(key);
        } catch (Exception e) {
            log.error("根据 key 获取缓存失败，当前key:[{}],失败原因:[{}]", key, e);
        }
        return null;
    }


    /**
     * 缓存set操作
     *
     * @param k    key
     * @param v    value
     * @param time time
     * @return boolean
     */
    public boolean cacheSet(String k, Object v, long time) {
        try {
            SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
            opsForSet.add(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("缓存 set 失败 当前 key:[{}] 失败原因 [{}]", k, e);
        }
        return false;
    }

    /**
     * 缓存set操作
     *
     * @param k    key
     * @param v    value
     * @param time time
     * @return boolean
     */
    public boolean cacheSet(String k, Object v, long time, TimeUnit timeUnit) {
        try {
            SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
            opsForSet.add(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            log.error("缓存 set 失败 当前 key:[{}] 失败原因 [{}]", k, e);
        }
        return false;
    }


    /**
     * 添加 set 缓存
     *
     * @param key   key
     * @param value value
     * @return true/false
     */
    public boolean cacheSet(String key, Object value) {
        return cacheSet(key, value, -1);
    }


    /**
     * 添加 缓存 set
     *
     * @param k    key
     * @param v    value
     * @param time 时间/秒
     * @return {@link boolean}
     */
    public boolean cacheSet(String k, Set<Object> v, long time) {
        try {
            SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
            opsForSet.add(k, v.toArray(new Object[0]));
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("缓存 set 失败 当前 key:[{}],失败原因 [{}]", k, e);
        }
        return false;
    }


    /**
     * 缓存 set
     *
     * @param k key
     * @param v value
     * @return {@link boolean}
     */
    public boolean cacheSet(String k, Set<Object> v) {
        return cacheSet(k, v, -1);
    }


    /**
     * 获取缓存set数据
     *
     * @param k key
     * @return set集合
     */
    public Set<Object> getSet(String k) {
        try {
            SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
            return opsForSet.members(k);
        } catch (Exception e) {
            log.error("获取缓存set失败 当前 key:[{}],失败原因 [{}]", k, e);
        }
        return Collections.emptySet();
    }

    /**
     * 缓存hash操作
     *
     * @param k       key
     * @param hashKey hashKey
     * @param v       value
     * @param time    time
     * @return boolean
     */
    public boolean cacheHash(String k, String hashKey, Object v, long time) {
        try {
            HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
            opsForHash.put(k, hashKey, v);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("缓存 hash 失败 当前 key:[{}] 失败原因 [{}]", k, e);
        }
        return false;
    }


    /**
     * 根据 key 自增hash 的 value
     *
     * @param key     key
     * @param hashKey hashKey
     * @param delta   增量
     * @return long
     */
    public Long incrHash(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 添加 Hash 缓存
     *
     * @param key     key
     * @param hashKey hashKey
     * @param value   value
     * @return true/false
     */
    public boolean cacheHash(String key, String hashKey, Object value) {
        return cacheHash(key, hashKey, value, -1);
    }


    /**
     * 添加 缓存 Hash
     *
     * @param k    key
     * @param v    value Map<String,String>
     * @param time 时间/秒
     * @return {@link boolean}
     */
    public boolean cacheHashAll(String k, Map<String, Object> v, long time) {
        try {
            HashOperations<String, String, Object> opsForHash = redisTemplate.opsForHash();
            opsForHash.putAll(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("缓存 hash 失败 当前 key:[{}],失败原因 [{}]", k, e);
        }
        return false;
    }


    /**
     * 缓存 hash all
     *
     * @param k key
     * @param v value Map<String,String>
     * @return {@link boolean}
     */
    public boolean cacheHashAll(String k, Map<String, Object> v) {
        return cacheHashAll(k, v, -1);
    }


    /**
     * 获取缓存hash数据
     *
     * @param k key
     * @return String
     */
    public Object getHash(String k, String hashKey) {
        try {
            HashOperations<String, String, Object> opsForHash = redisTemplate.opsForHash();
            return opsForHash.get(k, hashKey);
        } catch (Exception e) {
            log.error("获取缓存 hash 失败 当前 key:[{}],失败原因 [{}]", k, e);
        }
        return null;
    }

    /**
     * 获取缓存 hash 所有键值对数据
     *
     * @param k key
     * @return Map<String, String>
     */
    public Map<String, Object> getHashEntry(String k) {
        try {
            HashOperations<String, String, Object> opsForHash = redisTemplate.opsForHash();
            return opsForHash.entries(k);
        } catch (Exception e) {
            log.error("获取缓存 hash 失败 当前 key:[{}],失败原因 [{}]", k, e);
        }
        return Collections.emptyMap();
    }


    /**
     * list 缓存
     *
     * @param k    key
     * @param v    value
     * @param time 时间/秒
     * @return true/false
     */
    public boolean cacheList(String k, Object v, long time) {
        try {
            ListOperations<String, Object> opsForList = redisTemplate.opsForList();
            //此处为right push 方法/ 也可以 left push ..
            opsForList.rightPush(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("缓存list失败 当前 key:[{}],失败原因 [{}]", k, e);
        }
        return false;
    }


    /**
     * 缓存 list
     *
     * @param k key
     * @param v value
     * @return true/false
     */
    public boolean cacheList(String k, Object v) {
        return cacheList(k, v, -1);
    }


    /**
     * 缓存 list 集合
     *
     * @param k    key
     * @param v    value
     * @param time 时间/秒
     * @return {@link boolean}
     */
    public boolean cacheList(String k, List<Object> v, long time) {
        try {
            ListOperations<String, Object> opsForList = redisTemplate.opsForList();
            opsForList.rightPushAll(k, v);
            if (time > 0) {
                redisTemplate.expire(k, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("缓存list失败 当前 key:[{}],失败原因 [{}]", k, e);
        }
        return false;
    }


    /**
     * 缓存 list
     *
     * @param k key
     * @param v value
     * @return true/false
     */
    public boolean cacheList(String k, List<Object> v) {
        return cacheList(k, v, -1);
    }


    /**
     * 根据 key 获取 list 缓存
     *
     * @param k     key
     * @param start 开始
     * @param end   结束
     * @return 获取缓存区间内 所有value
     */
    public List<Object> getList(String k, long start, long end) {
        try {
            ListOperations<String, Object> opsForList = redisTemplate.opsForList();
            return opsForList.range(k, start, end);
        } catch (Exception e) {
            log.error("获取list缓存失败 当前 key:[{}],失败原因 [{}]", k, e);
        }
        return Collections.emptyList();
    }


    /**
     * 根据 key 获取总条数 用于分页
     *
     * @param key key
     * @return 条数
     */
    public long getListSize(String key) {
        try {
            ListOperations<String, Object> opsForList = redisTemplate.opsForList();
            return opsForList.size(key);
        } catch (Exception e) {
            log.error("获取list长度失败 key[{}],[{}]", key, e);
        }
        return 0;
    }


    /**
     * 获取总条数 用于分页
     *
     * @param listOps =redisTemplate.opsForList();
     * @param k       key
     * @return size
     */
    public long getListSize(ListOperations<String, String> listOps, String k) {
        try {
            return listOps.size(k);
        } catch (Exception e) {
            log.error("获取list长度失败 key[{}],[{}]", k, e);
        }
        return 0;
    }


    /**
     * 根据 key 移除 list 缓存
     *
     * @param k key
     * @return {@link boolean}
     */
    public boolean removeOneOfList(String k) {
        try {
            ListOperations<String, Object> opsForList = redisTemplate.opsForList();
            opsForList.rightPop(k);
            return true;
        } catch (Exception e) {
            log.error("移除list缓存失败 key[{}],[{}]", k, e);
        }
        return false;
    }


    /**
     * 根据 key 移除 value 缓存
     *
     * @param key key
     * @return true/false
     */
    public boolean removeValue(String key) {
        return remove(key);
    }


    /**
     * 根据 key 移除 set 缓存
     *
     * @param key key
     * @return true/false
     */
    public boolean removeSet(String key) {
        return remove(key);
    }


    /**
     * 根据 key 移除 list 缓存
     *
     * @param key key
     * @return true/false
     */
    public boolean removeList(String key) {
        return remove(key);
    }

    /**
     * 根据 key 自增value
     *
     * @param key key
     * @return true/false
     */
    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }


    /**
     * 移除缓存
     *
     * @param key key
     * @return boolean
     */
    private boolean remove(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            log.error("移除缓存失败 key:[{}] 失败原因 [{}]", key, e);
        }
        return false;
    }

}
