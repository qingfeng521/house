package com.imooc.house.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@PropertySource("classpath:properties/redis.properties")
public class SimpleRedisConfig {

    @Value("${spring.data.redis.ip}")
    private String ip;
    @Value("${spring.data.redis.port}")
    private String port;
    @Value("${spring.data.redis.maxTotal}")
    private String maxTotal;
    @Value("${spring.data.redis.maxIdle}")
    private String maxIdle;
    @Value("${spring.data.redis.maxWaitMillis}")
    private String maxWaitMillis;
    @Value("${spring.data.redis.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${spring.data.redis.testOnReturn}")
    private Boolean testOnReturn;

    @Bean
    public ShardedJedis getShardedJedis(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(Integer.parseInt(maxTotal));
        poolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        poolConfig.setMaxWaitMillis(Integer.parseInt(maxWaitMillis));
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnReturn(false);
        JedisShardInfo shardInfo1 = new JedisShardInfo(ip, Integer.parseInt(port), 500);
        List<JedisShardInfo> infoList = Arrays.asList(shardInfo1);
        ShardedJedisPool jedisPool = new ShardedJedisPool(poolConfig, infoList);
        return jedisPool.getResource();
    }

}
