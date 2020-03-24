package com.inspur.ihealth.codes.redis;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void redisTest() {
        redisTemplate.opsForValue().set("AUTH-TWO-RESULT:20190226000000101","1234567890");
        String retStr = String.valueOf(redisTemplate.opsForValue().get("AUTH-TWO-RESULT:20190226000000102"));
        log.info("AUTH-TWO-RESULT:   {}", retStr);
        if (Strings.isNullOrEmpty(retStr) || "null".equals(retStr)) {
            log.info("AUTH-TWO-RESULT is null");
        }
        log.info(retStr);

    }

    public static void main(String[] args) {
        Person p = new Person("wanglz","13800000000");
        Person p1 = null;
        System.out.println(String.valueOf(p.toString()));
        System.out.println(String.valueOf(p1));
    }

}
