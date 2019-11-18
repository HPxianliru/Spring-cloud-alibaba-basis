package com.xian.cloud.core.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.cloud.common.handler.DiscoveryClientControllerBackHandler;
import com.xian.cloud.common.handler.DiscoveryClientControllerFallBackHandler;
import com.xian.cloud.core.UserService;
import com.xian.cloud.dao.UserDao;
import com.xian.cloud.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: xlr
 * @Date: Created in 11:36 PM 2019/9/11
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    @Override
    @Transactional

    public String saveTx(UserEntity entity) {

        return saveTest();
    }

    @SentinelResource(
            value = "user:service:saveTx",
            blockHandler = "saveTx",
            fallback = "saveTx",
            blockHandlerClass = DiscoveryClientControllerBackHandler.class,
            fallbackClass = DiscoveryClientControllerFallBackHandler.class
    )
    private String saveTest(){
        return "success";
    }

}
