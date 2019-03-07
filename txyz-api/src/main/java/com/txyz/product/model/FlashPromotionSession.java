package com.txyz.product.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 限时场次表
 */
@Data
@TableName("sms_flash_promotion_session")
public class FlashPromotionSession {
    private Long id;
    private String name;
    private Date startTime;
    private Date endTime;
    private Integer status;
}
