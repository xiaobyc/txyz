package com.txyz.product.service;

import com.baomidou.mybatisplus.service.IService;
import com.txyz.product.model.FlashPromotion;

public interface FlashPromotionService extends IService<FlashPromotion> {

    /**
     * 首页显示需要的抢购数据
     * @return
     */
    public FlashPromotion selectCurrentPromotion();
}
