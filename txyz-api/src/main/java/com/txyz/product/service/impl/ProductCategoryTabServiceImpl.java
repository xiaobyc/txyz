package com.txyz.product.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.txyz.product.dao.ProductCategoryTabDao;
import com.txyz.product.model.ProductCategoryTab;
import com.txyz.product.service.ProductCategoryTabService;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryTabServiceImpl extends ServiceImpl<ProductCategoryTabDao,ProductCategoryTab> implements ProductCategoryTabService {
}
