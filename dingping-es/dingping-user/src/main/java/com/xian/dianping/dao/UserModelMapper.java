package com.xian.dianping.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xian.dianping.entity.UserModel;
import org.apache.ibatis.annotations.Param;

public interface UserModelMapper extends BaseMapper<UserModel> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserModel record);

    UserModel selectByPrimaryKey(Integer id);

    UserModel selectByTelphoneAndPassword(@Param("telphone") String telphone, @Param("password") String password);


    int updateByPrimaryKeySelective(UserModel record);


    int updateByPrimaryKey(UserModel record);

    Integer countAllUser();
}
