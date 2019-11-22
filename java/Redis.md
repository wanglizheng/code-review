# Redis存入，无法取出的问题
StringRedisTemplate，set 的时候 如果设置有效期，必须设置单位： TimeUnit.SECONDS

``````
stringRedisTemplate.opsForValue().set("test", "100",60*10,TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间
stringRedisTemplate.boundValueOps("test").increment(-1);//val做-1操作
stringRedisTemplate.opsForValue().get("test")//根据key获取缓存中的val
stringRedisTemplate.boundValueOps("test").increment(1);//val +1
stringRedisTemplate.getExpire("test")//根据key获取过期时间
stringRedisTemplate.getExpire("test",TimeUnit.SECONDS)//根据key获取过期时间并换算成指定单位
stringRedisTemplate.delete("test");//根据key删除缓存
stringRedisTemplate.hasKey("546545");//检查key是否存在，返回boolean值
stringRedisTemplate.opsForSet().add("red_123", "1","2","3");//向指定key中存放set集合
stringRedisTemplate.expire("red_123",1000 , TimeUnit.MILLISECONDS);//设置过期时间
stringRedisTemplate.opsForSet().isMember("red_123", "1")//根据key查看集合中是否存在指定数据
stringRedisTemplate.opsForSet().members("red_123");//根据key获取set集合

``````

代码示例：


````

package com.inspur.ihealth.user.rest.utils;

import com.google.common.base.Strings;
import com.inspur.ihealth.core.sms.feign.SMSClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class SmsComponet {

    private final StringRedisTemplate redisTemplate;
    private final SMSClient smsClient;

    private static final String SMS_VERIFY_COUNT = "verify_count_";
    private static final String SMS_AUTH_CODE_PWD = "verify_auth_code_pwd_";
    private static final Integer SMS_CODE_EXPIRE = 2 * 60;
    private static final Integer SMS_VERIFY_EXPIRE = 10 * 60;
    private static SmsUtils me = null;


    /**
     * 发送短信，并将短信放到redis，校验用
     */
    public Boolean sendAuthCode(String mobile,
                                int template) {

        String[] params = new String[1];
        params[0] = String.valueOf(new Random().nextInt(900000) + 100000);
        String codeStr = params[0];
        log.info("code save is: " + codeStr);
        //发送短信
        Boolean isSent = smsClient.sendSms(mobile, template, params);

        if (isSent) {
            redisTemplate.opsForValue().set(SMS_AUTH_CODE_PWD + mobile, codeStr, SMS_CODE_EXPIRE, TimeUnit.SECONDS);
        }

        return isSent;
    }

    /**
     * 验证短信验证码是否正确
     */

    public Boolean verifyAuthCode(String mobile, String code) throws Exception {

        veriyCount(mobile);

        String redisCode = redisTemplate.opsForValue().get(SMS_AUTH_CODE_PWD + mobile);
        log.info("redis code is: " + redisCode);
        if (!Strings.isNullOrEmpty(redisCode)) {
            if (redisCode.equals(code)) {
                redisTemplate.delete(SMS_AUTH_CODE_PWD + mobile);
                return true;
            }
        }
        return false;
    }

    /**
     * 验证次数限制,大于10次，10分钟后再试
     */

    public void veriyCount(String mobile) throws Exception {
        //redisTemplate.delete(SMS_VERIFY_COUNT + mobile);
        //判断redis是否有记录次数，没有set 1 次；有判断次数，超过10次抛出异常，低于10次，count ++
        String count = redisTemplate.opsForValue().get(SMS_VERIFY_COUNT + mobile);
        log.info("count is: " + count);
        if (!Strings.isNullOrEmpty(count)) {
            int _count = Integer.parseInt(count);
            if (_count > 10) throw new Exception("验证频繁，请十分钟后验证！");
            redisTemplate.opsForValue().set(SMS_VERIFY_COUNT + mobile, String.valueOf(_count + 1), SMS_VERIFY_EXPIRE, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(SMS_VERIFY_COUNT + mobile, "1", SMS_VERIFY_EXPIRE, TimeUnit.SECONDS);
        }


    }

}



````