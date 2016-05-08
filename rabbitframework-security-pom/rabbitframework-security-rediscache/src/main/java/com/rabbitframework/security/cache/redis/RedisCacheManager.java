package com.rabbitframework.security.cache.redis;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存管理实现{@link CacheManager}
 */
public class RedisCacheManager implements CacheManager {
    private static final Logger logger = LoggerFactory
            .getLogger(RedisCacheManager.class);
    // 保存缓存
    @SuppressWarnings("rawtypes")
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
    private RedisManager redisManager;
    // session的缓存时间默认3600秒即一小时
    private int sessionExpire = 3600;
    // 其它的缓存时间,默认600秒即10分钟
    private int otherExpire = 600;

    @SuppressWarnings("unchecked")
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug(" redis cache name is  " + name);
        synchronized (this) {
            Cache<K, V> cache = caches.get(name);
            if (cache == null) {
                int expire = otherExpire;
                if (RedisSessionDAO.ACTIVE_SESSION_CACHE_NAME.equals(name)) {
                    expire = sessionExpire;
                }
                cache = new RedisCache<K, V>(redisManager, expire);
                caches.put(name, cache);
            }
            return cache;
        }
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public void setOtherExpire(int otherExpire) {
        this.otherExpire = otherExpire;
    }

    public int getOtherExpire() {
        return otherExpire;
    }

    public void setSessionExpire(int sessionExpire) {
        this.sessionExpire = sessionExpire;
    }

    public int getSessionExpire() {
        return sessionExpire;
    }
}
