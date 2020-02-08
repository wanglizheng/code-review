package com.inspur.ihealth.codes.redislock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Integer defaultExpire = 20 * 1000;

    private static final String LOCK_TITLE = "redisLock_";
    private static final Integer LOCK_TRY_INTERVAL = 500;

    /**
     * 释放锁
     * @param lockName 锁Key
     */
    @Override
    public void unlock(String lockName) {
        remove(LOCK_TITLE + lockName);
    }

    /**
     * 删除对应的value
     * @param key
     */
    @Override
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 批量删除对应的value
     * @param keys
     */
    @Override
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    @Override
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(final String key, String value) {
        boolean result = true;
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存并设置过期时间
     * @param key
     * @param value
     * @param expireTime：过期时间
     * @return
     */
    @Override
    public boolean set(final String key, String value, Long expireTime) {
        boolean result = true;
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    @Override
    public Object get(final String key) {
        Object result = null;
        try {
            result = redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 默认失效时间30s
     * @param lockName
     * @return
     */
    @Override
    public Boolean lock(String lockName) {
        return this.tryLock(lockName, 0, defaultExpire, TimeUnit.MILLISECONDS);
    }

    @Override
    public Boolean tryLock(String key, long timeout, long expireTime, TimeUnit unit) {
        try {
            key = LOCK_TITLE + key;
            long startTimeMillis = System.currentTimeMillis();
            do {
                long newValue = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(expireTime, unit);
                //setIfAbsent 如果键不存在则新增,返回值true,存在则不改变已经有的值返回值false。
                Boolean isOk = redisTemplate.opsForValue().setIfAbsent(key, newValue + "");
                if (isOk) {
                    redisTemplate.expire(key, expireTime, unit);
                    return true;
                }
                // 获取过期时间
                String oldExpireTime = redisTemplate.opsForValue().get(key);
                if (null == oldExpireTime) {
                    oldExpireTime = "0";
                }
                long oldExpire = Long.parseLong(oldExpireTime);
                if (oldExpire >= System.currentTimeMillis()) {
                    //过了超时时间，直接返回false
                    if ((System.currentTimeMillis() - startTimeMillis) > timeout) {
                        return false;
                    }
                    Thread.sleep(LOCK_TRY_INTERVAL);
                }
                // 过了设置的失效时间，重新分配锁
                if (oldExpire < System.currentTimeMillis()) {
                    long newExpireTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(expireTime, unit);
                    String currentExpireTime = redisTemplate.opsForValue().getAndSet(key, newExpireTime + "");
                    if (null == currentExpireTime) {
                        currentExpireTime = "0";
                    }
                    if (currentExpireTime.equals(oldExpireTime)) {//通过redis操作的原子性保证只有一个线程拿到锁
                        // 获取到锁
                        redisTemplate.expire(key, expireTime, unit);
                        return true;
                    }
                }
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
