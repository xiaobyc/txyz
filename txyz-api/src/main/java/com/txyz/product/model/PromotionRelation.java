package com.txyz.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("sms_flash_promotion_product_relation")
public class PromotionRelation {
    @TableId("id")
    private Long id;
    @TableField("flash_promotion_id")
    private Long promotionId;
    @TableField("product_id")
    private Long productId;
    @TableField("current_price")
    private Float currentPrice;
    @TableField("current_img")
    private String image;
    @TableField("flash_promotion_price")
    private Float flashPrice;
    @TableField("flash_promotion_img")
    private String flashImage;
    @TableField("flash_promotion_count")
    private Integer count;
    @TableField("flash_promotion_limit")
    private Integer perLimit;
    @TableField("sort")
    private Integer sortNum;
}
