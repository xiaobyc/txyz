package com.txyz.product.rediskey;

/**
 * 商品相关缓存key
 */
public class ProductRedisKey {
    //商品目录缓存
    private static final String PRODUCT_CATE="product_cate";

    private static  final String PRODUCT_BANNER="product_banner";

    private static final String INDEX_PRODUCT_MODULE="index_product_module";

    /**
     * 首页商品目录key
     * @return
     */
    public static String getProductCate(){
        return PRODUCT_CATE;
    }

    /**
     * 首页banner缓存
     * @return
     */
    public static  String getProductBanner(){
        return PRODUCT_BANNER;
    }

    /**
     * 首页module 缓存
     * @return
     */
    public static String getIndexProductModulekey(){
        return INDEX_PRODUCT_MODULE;
    }
}
