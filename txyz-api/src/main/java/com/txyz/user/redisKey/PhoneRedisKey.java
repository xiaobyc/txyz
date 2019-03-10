package com.txyz.user.redisKey;

/**
 * 手机号注册短信缓存key
 */
public interface PhoneRedisKey {
    //发短信手机缓存
    public String PHONE="phone_";
    //发短信验证码缓存
    public String PHONE_RANDOM="phone_random_";

    /**
     * 根据手机号获取手机验证码缓存key
     * @param phone
     * @return
     */
    public static String getPhoneRandom(String phone){
        return PHONE_RANDOM+phone;
    }
}
