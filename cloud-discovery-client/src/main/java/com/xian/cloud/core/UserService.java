package com.xian.cloud.core;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.cloud.entity.UserEntity;

/**
 * @Author: xlr
 * @Date: Created in 11:35 PM 2019/9/11
 */
public interface UserService extends IService<UserEntity> {



    String saveTx(UserEntity entity);
}
