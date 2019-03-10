package com.txyz.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("system_round_button_area")
public class ButtonArea {
    @TableId("function_id")
    private Long functionId;
    @TableField("function_name")
    private String name;
    @TableField("function_type")
    private String type;
    @TableField("function_icon_image_url")
    private String imgUrl;
    @TableField("function_linked_url")
    private String url;
    @TableField("view_order")
    private Integer viewUrl;
    @TableField("category_module_id")
    private Long moduleId;

}
