package com.txyz.user.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 用户药店表
 */
@Data
@TableName("txyz_user_pharmacy")
public class UserPharmacy {
    @TableId("id")
    private Long id;
    @TableField("type")
    private String type;
    @TableField("name")
    private String name;
    @TableField("lat")
    private String lat;
    @TableField("lng")
    private String lng;
    @TableField("address")
    private String address;
    @TableField("user_id")
    private Long userId;
    @TableField("provice_code")
    private String proviceCode;
    @TableField("provice_name")
    private String proviceName;
    @TableField("city_code")
    private String cityCode;
    @TableField("city_name")
    private String cityName;
    @TableField("area_code")
    private String areaCode;
    @TableField("area_name")
    private String areaName;
    @TableField("address_img")
    private String addressImg;
    @TableField("cert_img")
    private String certImg;
    @TableField("other_img")
    private String otherImg;

}
