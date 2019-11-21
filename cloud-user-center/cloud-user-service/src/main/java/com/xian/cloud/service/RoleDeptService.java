package com.xian.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.cloud.entity.RoleDeptEntity;

import java.util.List;

/**
 * <p>
 * 角色与部门对应关系 服务类
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
public interface RoleDeptService extends IService<RoleDeptEntity> {

    /**
     * 根据角色id查询部门ids
     * @param roleId
     * @return
     */
    List<RoleDeptEntity> getRoleDeptIds(int roleId);
}
