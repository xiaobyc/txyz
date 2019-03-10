package com.txyz.product.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.txyz.product.dao.FlashPromotionDao;
import com.txyz.product.model.FlashPromotion;
import com.txyz.product.service.FlashPromotionService;
import org.springframework.stereotype.Service;

@Service
public class FlashPromotionServiceImpl extends ServiceImpl<FlashPromotionDao,FlashPromotion> implements FlashPromotionService {
    @Override
    public FlashPromotion selectCurrentPromotion() {
        EntityWrapper<FlashPromotion> wrapper = new EntityWrapper<>();
        FlashPromotion search =  new FlashPromotion();
        return null;
    }
}
