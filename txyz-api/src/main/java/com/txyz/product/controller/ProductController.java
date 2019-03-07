package com.txyz.product.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.txyz.common.anno.UserLoginToken;
import com.txyz.common.cache.redis.RedisUtils;
import com.txyz.common.cache.redis.TimeRedisKey;
import com.txyz.common.constant.Cons;
import com.txyz.common.result.R;
import com.txyz.product.model.ProductBanner;
import com.txyz.product.model.ProductCategoryModule;
import com.txyz.product.model.ProductCategoryTab;
import com.txyz.product.rediskey.ProductRedisKey;
import com.txyz.product.service.ProductBannerService;
import com.txyz.product.service.ProductCategoryModuleService;
import com.txyz.product.service.ProductCategoryTabService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "product")
public class ProductController {

    @Autowired
    private RedisUtils redisUtils;






}
