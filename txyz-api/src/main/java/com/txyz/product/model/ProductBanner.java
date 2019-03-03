package com.txyz.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@TableName("system_product_banner")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductBanner {
    @TableId("id")
    private Integer id;
    @TableField("image_url")
    private String imageUrl;
    @TableField("priTitle")
    private String priTitle;
    @TableField("secTitle")
    private String secTitle;
    @TableField("bannerType")
    private String bannerType;
    @TableField("linked_url")
    private String linkedUrl;
    @TableField("category_module_id")
    private Integer moduleId;
}
