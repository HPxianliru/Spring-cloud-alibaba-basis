package com.xian.cloud.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.cloud.entity.RoleDeptEntity;
import com.xian.cloud.dao.RoleDeptMapper;
import com.xian.cloud.service.RoleDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色与部门对应关系 服务实现类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Service
public class RoleDeptServiceImpl extends ServiceImpl<RoleDeptMapper, RoleDeptEntity> implements RoleDeptService {


    @Override
    public List<RoleDeptEntity> getRoleDeptIds(int roleId) {
        return baseMapper.selectList(Wrappers.<RoleDeptEntity>lambdaQuery().select(RoleDeptEntity::getDeptId).eq(RoleDeptEntity::getRoleId,roleId));
    }
}
