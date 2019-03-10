package com.txyz.common.redisKey;

/**
 * Created by alex on 2019/1/3.
 * 缓存时间静态常量设置
 */

public class TimeRedisKey {
    //缓存一小时
    public static  final Long HOURS=3600l;
    //缓存半天
    public static  final Long HALFDAYS=43200l;
    //缓存一天
    public static  final Long DAYS=86400l;
    //缓存一周
    public static  final Long WEEKS=604800l;
    //缓存一月
    public static  final Long MONTHS=2592000l;
    //5分钟
    public static final Long FIVE_MINS=300l;
}
