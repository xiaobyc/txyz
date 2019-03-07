package com.txyz.product.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.txyz.product.dao.ProductDao;
import com.txyz.product.model.Product;
import com.txyz.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao,Product> implements ProductService {
}
