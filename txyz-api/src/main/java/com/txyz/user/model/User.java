package com.txyz.user.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("txyz_user")
public class User {
    @TableField("user_id")
    private Long userId;
    @TableField("user_account")
    private String account;
    @TableField("email")
    private String email;
    @TableField("phone")
    private String phone;
    @TableField("password")
    private String password;
    @TableField("create_time")
    private Date createTime;
    @TableField("birthday")
    private Date birthday;
    @TableField("money")
    private Double money;
    @TableField("name")
    private String nickName;
    private Integer lvId;
    private String sex;
    private String userType;

}
