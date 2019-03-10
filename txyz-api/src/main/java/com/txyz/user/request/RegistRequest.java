package com.txyz.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegistRequest {
    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "重复密码不能为空")
    private String rePassword;
    @NotNull(message = "验证码不能为空")
    private String randomCode;

    private String invitCode;
}
