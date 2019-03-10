package com.txyz.baseData.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by alex on 2019/3/7.
 */
@TableName("txyz_area")
@Data
public class Area {
    @TableId("area_id")
    private Integer areaId;
    //行政编号
    private String areaCode;
    //名称
    private String areaName;
    //父节点
    private String pId;
}
