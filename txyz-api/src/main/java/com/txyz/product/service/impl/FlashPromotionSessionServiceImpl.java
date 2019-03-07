package com.txyz.product.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.txyz.product.dao.FlashPromotionSessionDao;
import com.txyz.product.model.FlashPromotionSession;
import com.txyz.product.service.FlashPromotionSessionService;
import org.springframework.stereotype.Service;

@Service
public class FlashPromotionSessionServiceImpl extends ServiceImpl<FlashPromotionSessionDao,FlashPromotionSession> implements FlashPromotionSessionService {
}
