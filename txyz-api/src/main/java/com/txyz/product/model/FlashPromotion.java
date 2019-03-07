package com.txyz.product.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 限时购表
 */
@TableName("sms_flash_promotion")
@Data
public class FlashPromotion {
    @TableId("id")
    private Long id;
    @TableField("title")
    private String title;
    @TableField("start_date")
    private Date startDate;
    @TableField("end_date")
    private Date endDate;
    @TableField("status")
    private Integer status;
    @TableField("start_time")
    private Date startTime;
    @TableField("end_time")
    private Date endTime;
    @TableField("start_date_time")
    private Date startDateTime;
    @TableField("end_date_time")
    private Date endDateTime;
}
