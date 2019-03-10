package com.txyz.user.utils;

import com.alibaba.fastjson.JSONObject;
import com.txyz.common.cache.redis.RedisUtils;
import com.txyz.common.constant.ResponseCode;
import com.txyz.common.exception.RException;
import com.txyz.common.redisKey.TimeRedisKey;
import com.txyz.common.utils.HttpUtils;
import com.txyz.user.redisKey.PhoneRedisKey;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.security.provider.MD5;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 */
@Component
public class SendMessageUtils {
    private static Logger logger= LoggerFactory.getLogger(SendMessageUtils.class);
    @Value("${message.url}")
    private String url;
    @Value("${message.appId}")
    private String appId;
    @Value("${message.appSecret}")
    private String appSecret;
    @Value("${message.templateCode}")
    private String templateCode;
    @Value("${message.signName}")
    private String signName;

    @Autowired
    private RedisUtils redisUtils;



    public  String sendMessage(String phone){
        //生成验证码
        String random = this.genRandom(phone);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
       String timeStamp =  sdf.format(new Date());
       String nonce =String.valueOf(System.currentTimeMillis());
       String signSrc=nonce+timeStamp+appId+appSecret;
       String sign =DigestUtils.md5Hex(signSrc).toUpperCase();
        String result =null;
        Map<String,String> param = new HashMap<>();
        param.put("nonce",nonce);
        param.put("appId",appId);
        param.put("timestamp",timeStamp);
        param.put("sign",sign);
        param.put("phone",phone);
        param.put("signName",signName);
        param.put("templateCode",templateCode);
        param.put("templateParam","{'code':'"+random+"'}");
        try {
            if(logger.isInfoEnabled()){
                logger.info("发短信,url="+url+" param="+param.toString());
            }
             result = HttpUtils.doPostMessage(url, param);
            if(logger.isInfoEnabled()){
                logger.info("发短信返回:result="+result);
            }
        } catch (IOException e) {
            throw new RException(ResponseCode.ERROR,"发送短信失败");
        }
        //对返回信息进行解析
        if (StringUtils.isNotEmpty(result)){
            JSONObject resultObj = JSONObject.parseObject(result);
            if(resultObj.getInteger("result")==0){
                String phoneRandomKey =PhoneRedisKey.getPhoneRandom(phone);
                redisUtils.set(phoneRandomKey,random,TimeRedisKey.FIVE_MINS);
                return  random;
            }
        }else {
            throw new RException(ResponseCode.ERROR,"发送短信失败");
        }
        return  null;
    }

    /**
     * 生成验证码
     * @return
     */
    public  String genRandom(String phone){
        String key = PhoneRedisKey.getPhoneRandom(phone);
        String randomCode = redisUtils.get(key);
        if(StringUtils.isEmpty(randomCode)){
            randomCode = String.valueOf((int)((Math.random()*9+1)*100000));
        }
        return  randomCode;
    }

    /**
     * 32位MD5加密的大写字符串
     *
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
