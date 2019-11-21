package com.xian.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.cloud.entity.UserRoleEntity;
import com.xian.cloud.dao.UserRoleMapper;
import com.xian.cloud.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {


    @Override
    public boolean save(UserRoleEntity entity) {
        return super.save(entity);
    }


    @Override
    public List<UserRoleEntity> selectUserRoleListByUserId(Integer userId) {
        return baseMapper.selectUserRoleListByUserId(userId);
    }
}
