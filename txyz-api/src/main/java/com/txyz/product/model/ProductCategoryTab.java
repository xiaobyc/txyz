package com.txyz.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "system_category_tab")
public class ProductCategoryTab {
    @TableId("category_id")
    private Integer categoryId;
    //目录名称
    @TableField("category_name")
    private String categoryName;
    //目录排序
    @TableField("view_order")
    private Integer viewOrder;
}
