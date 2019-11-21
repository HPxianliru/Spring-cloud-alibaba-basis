package com.xian.cloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xian.cloud.entity.MenuEntity;
import com.xian.cloud.entity.RoleEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author xlr
 * @since 2019-04-21
 */
@Repository
public interface RoleMapper extends BaseMapper<RoleEntity> {


    @Insert("insert into sys_role (role_name,role_code,role_desc,ds_type,ds_scope) values (#{roleName}, #{roleCode},#{roleDesc},#{dsType},#{dsScope})")
    @Options(useGeneratedKeys=true, keyProperty="roleId", keyColumn="role_id")
    Boolean insertRole(RoleEntity sysRole);

    /**
     *
     * @param roleId
     * @return
     */
    @Select("select m.menu_id,m.name,m.type,m.parent_id,m.sort,m.perms from sys_menu m, sys_role_menu rm where rm.role_id = #{roleId} and m.menu_id = rm.menu_id")
    List<MenuEntity> findMenuListByRoleId(int roleId);

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    @Select("SELECT r.* FROM sys_role r, sys_user_role ur WHERE r.role_id = ur.role_id AND r.del_flag = 0 and  ur.user_id IN (#{userId})")
    List<RoleEntity> listRolesByUserId(Integer userId);
}
