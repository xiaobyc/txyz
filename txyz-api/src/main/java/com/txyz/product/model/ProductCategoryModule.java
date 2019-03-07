package com.txyz.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName(value = "system_product_category_module")
public class ProductCategoryModule implements Serializable {
    @TableId("module_id")
    private Integer moduleId;
    @TableField("module_type_id")
    private Integer moduleTypeId;
    @TableField("module_name")
    private String moduleName;
    @TableField("module_priority")
    private Integer modulePriority;
    @TableField("module_banner_image_url")
    private String moduleBannerUrl;
    @TableField("module_theme_image_url")
    private String moduleThemeUrl;
    @TableField("module_linked_url")
    private String moduleUrl;
}
