package com.inspur.ihealth.codes.redislock;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    void unlock(String lockName);

    void remove(String key);

    void remove(String... keys);

    boolean exists(String key);

    boolean set(String key, String value);

    boolean set(String key, String value, Long expireTime);

    Object get(String key);

    Boolean lock(String lockName);

    Boolean tryLock(String key, long timeout, long expireTime, TimeUnit unit);
}
