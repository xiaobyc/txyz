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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("index")
@Slf4j
public class IndexController {
    @Autowired
    private ProductCategoryTabService productCategoryTabService;
    @Autowired
    private ProductBannerService productBannerService;
    @Autowired
    private ProductCategoryModuleService moduleService;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 首页顶部药品列表
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "indexProductCateList",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R indexProductCateList(){
        R r = null;
        String key = ProductRedisKey.getProductCate();
        String value= redisUtils.get(key);
        if(StringUtils.isNotEmpty(value)){
            r = R.ok().put("cate", JSONArray.parseArray(value,ProductCategoryTab.class));
        }else {
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.where("view_show",1);
            wrapper.orderBy("view_order",false);
            List<ProductCategoryTab> list = productCategoryTabService.selectList(wrapper);
            if(list!=null){
                redisUtils.set(key,JSONArray.toJSONString(list),TimeRedisKey.DAYS);
                r = R.ok().put("list",list);
            }
        }
        return r;
    }

    /**
     * 首页banner列表
     * @return
     */
    @RequestMapping(value = "indexBannerList",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R indexBannerList(){
        String key = ProductRedisKey.getProductBanner();
        String value = redisUtils.get(key);
        List<ProductBanner> list = null;
        R r = null;
        if(StringUtils.isNotEmpty(value)){
            list = JSONArray.parseArray(value,ProductBanner.class);
            r =R.ok().put("list",list);
        }else {
            list = productBannerService.selectList(null);
            if(list!=null&&list.size()>0){
                redisUtils.set(key,JSONArray.toJSONString(list),TimeRedisKey.DAYS);
                r =R.ok().put("list",list);
            }else {
                r =R.noData();
            }
        }
        return  r;
    }

    /**
     * 首页商品模块列表
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "indexModuleList",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R indexModuleList(){
        String key = ProductRedisKey.getIndexProductModulekey();
        String value = redisUtils.get(key);
        List<ProductCategoryModule> list = null;
        if(StringUtils.isNotEmpty(value)){
            list =JSONArray.parseArray(value,ProductCategoryModule.class);
        }else {
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.orderBy("module_priority",true);
            list =  moduleService.selectList(wrapper);
            if(list!=null&&list.size()>0){
                redisUtils.set(key,JSONArray.toJSONString(list),TimeRedisKey.DAYS);
            }
        }
        return R.ok().put("list",list);
    }
}
