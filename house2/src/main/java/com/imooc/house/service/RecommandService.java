package com.imooc.house.service;

import com.imooc.house.Constants;
import com.imooc.house.common.PageParams;
import com.imooc.house.entity.HouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommandService {
//    @Autowired
//    private JedisCluster jedisCluster;

    @Autowired
    private ShardedJedis shardedJedis;

    @Autowired
    private HouseService houseService;

    public void increase(Long id){
//        jedisCluster.zincrby(Constants.HOT_HOUSE_KEY,1.0,id+"");
//        jedisCluster.zremrangeByRank(Constants.HOT_HOUSE_KEY,10,-1);
        shardedJedis.zincrby(Constants.HOT_HOUSE_KEY,1.0,id+"");
        shardedJedis.zremrangeByRank(Constants.HOT_HOUSE_KEY,10,-1);
    }

    public List<Long> getHot(){
        Set<String> hotSet = shardedJedis.zrevrange(Constants.HOT_HOUSE_KEY,0,-1);
        return hotSet.stream().map(Long::parseLong).collect(Collectors.toList());
    }

    public List<HouseEntity> queryHotHouse(Integer size){
        HouseEntity houseEntity = new HouseEntity();
        List<Long> orderIdList = getHot();
        houseEntity.setIds(orderIdList);
        List<HouseEntity> hotHouseList =  houseService.queryAndSetImag(houseEntity, PageParams.build(size,1));
        List<HouseEntity> orderHouseList = new ArrayList<HouseEntity>();
        for(Long id : orderIdList){
            for(HouseEntity hhl:hotHouseList){
                if(id == hhl.getId()){
                    orderHouseList.add(hhl);
                    break;
                }
            }
        }
        return orderHouseList;
    }

    public List<HouseEntity> getLastHouses() {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setSort("create_time");
        List<HouseEntity> houseEntityList = houseService.queryAndSetImag(houseEntity,PageParams.build(10,1));
        return houseEntityList;

    }
}
