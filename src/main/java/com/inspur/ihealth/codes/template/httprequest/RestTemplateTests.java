package com.inspur.ihealth.codes.template.httprequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTests {

    // 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例

    @Autowired
    RestTemplate restTemplate;
}
