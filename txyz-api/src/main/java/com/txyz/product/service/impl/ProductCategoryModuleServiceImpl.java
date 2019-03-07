package com.txyz.product.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.txyz.product.dao.ProductCategoryModuleDao;
import com.txyz.product.model.ProductCategoryModule;
import com.txyz.product.service.ProductCategoryModuleService;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryModuleServiceImpl extends ServiceImpl<ProductCategoryModuleDao,ProductCategoryModule> implements ProductCategoryModuleService {
}
