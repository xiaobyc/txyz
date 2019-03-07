package com.txyz.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@TableName("pms_product")
@Data
public class Product {
    @TableId("id")
    private Long id;
    @TableField("brand_id")
    private Long brandId;
    @TableField("product_category_id")
    private Long productCId;
    @TableField("product_attribute_category_id")
    private Long proAcId;
    @TableField("name")
    private String name;
    @TableField("pic")
    private String pic;
    @TableField("product_sn")
    private String productSn;
    @TableField("new_status")
    private Integer newStatus;
    @TableField("recommand_status")
    private Integer recommandStatus;
    @TableField("verify_status")
    private Integer verifyStatus;
    @TableField("sort")
    private Integer sort;
    @TableField("sale")
    private Integer sale;
    @TableField("price")
    private Float price;
    @TableField("promotion_price")
    private Float promotionPrice;
    @TableField("gift_growth")
    private Integer giftGrowth;
    @TableField("gift_point")
    private Integer giftPoint;
    @TableField("use_point_limit")
    private Integer usePointLimit;
    @TableField("sub_title")
    private String subTitle;
    @TableField("description")
    private String description;
    @TableField("original_price")
    private Float originalPrice;
    @TableField("stock")
    private Integer stock;
    @TableField("low_stock")
    private Integer lowStock;
    @TableField("unit")
    private String unit;
    @TableField("weight")
    private Float weight;
    @TableField("preview_status")
    private Integer previewStatus;
    @TableField("service_ids")
    private String servicesIds;
    @TableField("keywords")
    private String keyWords;
    @TableField("note")
    private String note;
    @TableField("album_pics")
    private String albumPics;
    @TableField("etail_title")
    private String detailTitle;
    @TableField("detail_desc")
    private String detailDesc;
    @TableField("detail_html")
    private String detailHtml;
    @TableField("detail_mobile_html")
    private String detailMobileHtml;
    @TableField("promotion_start_time")
    private Date promotionStartTime;
    @TableField("promotion_end_time")
    private Date promotionEndTime;
    @TableField("promotion_per_limit")
    private Integer promotionPerLimit;
    @TableField("promotion_type")
    private Integer promotionType;
    @TableField("brand_name")
    private String brandName;
    @TableField("product_category_name")
    private String productCName;
}
