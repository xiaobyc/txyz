package com.txyz.product.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.txyz.product.dao.ButtonAreaDao;
import com.txyz.product.model.ButtonArea;
import com.txyz.product.service.ButtonAreaService;
import org.springframework.stereotype.Service;

@Service
public class ButtonAreaServiceImpl extends ServiceImpl<ButtonAreaDao,ButtonArea> implements ButtonAreaService {
}
