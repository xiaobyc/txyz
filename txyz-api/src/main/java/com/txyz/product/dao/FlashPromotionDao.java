package com.txyz.product.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.txyz.product.model.FlashPromotion;

import java.util.Date;
import java.util.List;

public interface FlashPromotionDao extends BaseMapper<FlashPromotion> {

    public List<FlashPromotion> selectCurrentPromotion(Date currentDate, Date currentTime);
}
