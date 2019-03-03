package com.txyz.product.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.txyz.product.dao.ProductBannerDao;
import com.txyz.product.model.ProductBanner;
import com.txyz.product.service.ProductBannerService;
import org.springframework.stereotype.Service;

@Service
public class ProductBannerServiceImpl extends ServiceImpl<ProductBannerDao,ProductBanner> implements ProductBannerService {
}
