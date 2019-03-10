package com.txyz.user.request;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 用户药店表
 */
@Data
public class UserPharmacyRequest {
    private Long id;
    private String type;
    private String name;
    private String lat;
    private String lng;
    private String address;
    private Long userId;
    private String proviceCode;
    private String proviceName;
    private String cityCode;
    private String cityName;
    private String areaCode;
    private String areaName;
    private String certImg;
    private String otherImg;
    private String addressImg;
}
