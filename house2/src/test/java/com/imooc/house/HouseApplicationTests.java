package com.imooc.house;

import com.imooc.house.common.BeanHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseApplicationTests {

    @Autowired
    ShardedJedis shardedJedis;

    @Test
    public void contextLoads() {
        ShardedJedis jedis = null;
        try {
            shardedJedis.set("test222","test222");
            shardedJedis.set("test1","test1");
            String str = shardedJedis.get("test222");
            System.out.println(str);
        } finally {
            BeanHelper.closeJedis(shardedJedis);
        }
    }

}
