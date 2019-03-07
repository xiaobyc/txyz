package com.txyz.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class LoginRequest {
    @NotNull(message = "用户名不能为空")
    private String userName;
    @NotNull(message = "用户密码不能为空")
    private String password;
}
