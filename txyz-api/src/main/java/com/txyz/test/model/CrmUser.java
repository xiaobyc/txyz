package com.txyz.test.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName(value = "crm_user")
public class CrmUser {
    @TableId(value = "uc_id")
    private String ucId;
    @TableField("avatar")
    private String avatar;
    @TableField(value = "user_id")
    private String userId;
}
