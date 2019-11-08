package com.xian.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xlr
 * @Date: Created in 11:24 PM 2019/9/11
 */
@Data
@TableName("sys_user")
public class UserEntity extends Model<UserEntity>{

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String username;

    private String passWord;

    private String email;

    @Override
    protected Serializable pkVal() {
        /**
         * AR 模式这个必须有，否则 xxById 的方法都将失效！
         * 另外 UserMapper 也必须 AR 依赖该层注入，有可无 XML
         */
        return id;
    }
}
