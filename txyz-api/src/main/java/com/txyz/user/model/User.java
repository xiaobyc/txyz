package com.txyz.user.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;
@Data
@TableName("txyz_user")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class User {
    @TableId("user_id")
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
    @TableField("lv_id")
    private Integer lvId;
    @TableField("sex")
    private String sex;
    @TableField("user_status")
    private String userStatus;
    @TableField("invit_code")
    private String invitCode;
    @TableField(exist = false)
    private String userStep;

}
