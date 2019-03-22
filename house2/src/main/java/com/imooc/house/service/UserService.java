package com.imooc.house.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.imooc.house.Constants;
import com.imooc.house.common.BeanHelper;
import com.imooc.house.common.HashUtil;
import com.imooc.house.common.StringUtil;
import com.imooc.house.entity.UserEntity;
import com.imooc.house.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private FileService fileService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailService mailService;

    private final Cache<String,String> registerCache = CacheBuilder.newBuilder()
            .maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, String>() {
        @Override
        public void onRemoval(RemovalNotification<String, String> removalNotification) {
            userMapper.delete(removalNotification.getValue());
        }
    }).build();
    /*
        1. 插入数据库  非激活状态 密码加盐md5 保存头像到本地
        2. 生成key 绑定email
        3. 发送邮件到用户邮箱
     */
    @Transactional(rollbackFor=Exception.class)
    public Boolean addUser(UserEntity user){
        user.setPasswd(HashUtil.encryPasswd(user.getPasswd()));
        List<String> imagList = fileService.getImagPath(Lists.newArrayList(user.getAvatarFile()));
        if(imagList!=null){
            user.setAvatar(imagList.get(0));
        }
        BeanHelper.setDefaultProp(user,UserEntity.class);
        user.setCreateTime(StringUtil.date2String(new Date()));
        user.setEnable(0);
        userMapper.insert(user);
        mailService.registerNotify(user.getEmail());
        return true;
    }

    /*
       缓存key email 的关系
       借助spring mail 发送邮件
       借助异步框架进行异步操作
     */
    @Async
    public void registerNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey,email);
        String url = "http://" + Constants.DOMAIN_NAME + "/accounts/verify?key=" + randomKey;
        mailService.sendMail("好房网",url,email);
    }

    public Boolean enable(String key) {
        Boolean flag = mailService.enable(key);
        if(flag){
            return true;
        }
        return false;
    }

    public UserEntity checkUserName(String userName, String passwd) {
        UserEntity user = new UserEntity();
        user.setEmail(userName);
        user.setPasswd(HashUtil.encryPasswd(passwd));
        user.setEnable(1);
        List<UserEntity> userList = getUserByQuery(user);
        if(!userList.isEmpty()){
            return userList.get(0);
        }
        return null;
    }

    private List<UserEntity> getUserByQuery(UserEntity user) {
       List<UserEntity> userList = userMapper.selectUsersByQuery(user);
        userList.forEach(u ->{
            u.setAvatar(Constants.FILE_URL + u.getAvatar());
        });
        return userList;
    }

    public void updateUser(UserEntity updateUser) {
        updateUser.setCreateTime(StringUtil.date2String(new Date()));
        userMapper.updateByEmail(updateUser);
    }

    public List<UserEntity> queryUserByEntity(UserEntity queryUser) {
        return userMapper.selectUsersByQuery(queryUser);
    }
}
