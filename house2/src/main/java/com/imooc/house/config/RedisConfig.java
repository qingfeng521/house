package com.imooc.house.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

//@Configuration
@PropertySource("classpath:properties/redis.properties")
public class RedisConfig {
    @Value("${spring.data.redis.nodes}")
    private String redisNodes;
    //@Bean
    public JedisCluster getJedisCluster(){
        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        String[] strArray = redisNodes.split(",");
        for(String str: strArray){
            String[] ipArray = str.split(":");
            hostAndPortSet.add(new HostAndPort(ipArray[0],Integer.parseInt(ipArray[1])));
        }
        JedisCluster jedisCluster = new JedisCluster(hostAndPortSet);
        return jedisCluster;
    }
}
