package com.inspur.ihealth.codes.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class RedisConfig {

    @Value("${spring.redis.cluster.nodes}")
    private String nodes;

    /**
     * 公共连接池
     */
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig getRedisConfig() {
        return new JedisPoolConfig();
    }

    public JedisConnectionFactory getConnectionFactoryCluster() {
        JedisPoolConfig jedisPoolConfig = this.getRedisConfig();
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        if (StringUtils.isNotBlank(nodes)) {
            List<RedisNode> nodeList = new ArrayList<>();
            String[] cNodes = nodes.split(",");
            for (String node : cNodes) {
                String[] hp = node.split(":");
                nodeList.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
            }
            redisClusterConfiguration.setClusterNodes(nodeList);
        }
        return new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
    }

    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getSingleConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        return factory;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory;
        if (StringUtils.isNotBlank(nodes)) {
            factory = getConnectionFactoryCluster();
        } else {
            factory = getSingleConnectionFactory();
        }
        return factory;
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        JedisConnectionFactory jedisConnectionFactory = getConnectionFactory();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }
}