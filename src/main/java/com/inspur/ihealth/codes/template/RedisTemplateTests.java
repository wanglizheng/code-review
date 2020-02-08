package com.inspur.ihealth.codes.template;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

@RequiredArgsConstructor
public class RedisTemplateTests {

    /**
     * 1、spring-boot-starter-data-redis 依赖加入pom.xml
     * 2、如果无法注入，采用@Resource注入
     * 3、如果还无法注入，在入口函数添加注解：@ComponentScan(basePackages = {"org.springframework.data.redis"})
     *
     *
     * stringRedisTemplate.opsForValue().set("test", "100",60*10,TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间
     * stringRedisTemplate.boundValueOps("test").increment(-1);//val做-1操作
     * stringRedisTemplate.opsForValue().get("test")//根据key获取缓存中的val
     * stringRedisTemplate.boundValueOps("test").increment(1);//val +1
     * stringRedisTemplate.getExpire("test")//根据key获取过期时间
     * stringRedisTemplate.getExpire("test",TimeUnit.SECONDS)//根据key获取过期时间并换算成指定单位
     * stringRedisTemplate.delete("test");//根据key删除缓存
     *
     * stringRedisTemplate.hasKey("546545");//检查key是否存在，返回boolean值
     * stringRedisTemplate.opsForSet().add("red_123", "1","2","3");//向指定key中存放set集合
     * stringRedisTemplate.expire("red_123",1000 , TimeUnit.MILLISECONDS);//设置过期时间
     * stringRedisTemplate.opsForSet().isMember("red_123", "1")//根据key查看集合中是否存在指定数据
     * stringRedisTemplate.opsForSet().members("red_123");//根据key获取set集合
     *
     * https://www.cnblogs.com/slowcity/p/9002660.html
     */

    private final StringRedisTemplate redisTemplate;

    public void redisTemplateTests() {
        redisTemplate.delete("001");
        redisTemplate.opsForSet().add("red_123","1","2");


        String userNum = redisTemplate.opsForValue().get("userNum");
        if(Strings.isNullOrEmpty(userNum)){
            redisTemplate.opsForValue().set("userNum", userNum);
        }

    }


}
