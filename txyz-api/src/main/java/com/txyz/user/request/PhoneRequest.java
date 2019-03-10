package com.txyz.user.request;

import lombok.Data;

@Data
public class PhoneRequest {
    private String phone;
    private String randomCode;
    private String password;
    private String rePassword;
    private Long userId;
}
