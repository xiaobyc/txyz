package com.txyz.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.txyz.common.exception.RException;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 2019/3/4.
 * App 登录token生成和解析
 */

public class JwtToken {
    /**
     * token 秘钥
     */
    public static  final String SECRET="JKKLJOoasdlfj";
    /**
     * token 过期时间 10天
     */
    public static final  int calendarField= Calendar.DATE;

    public static final int calendarInterval=10;


    public static String createToken(String userId) throws  Exception{
        Date date = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField,calendarInterval);
        Date expireDate = nowTime.getTime();

        Map<String,Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("type","JWT");
        String token = JWT.create().withHeader(map)
                       .withClaim("iss","Service")
                .withClaim("txyz", "APP")
                .withClaim("userId", null == userId ? null : userId.toString())
                .withIssuedAt(date) // sign time
                .withExpiresAt(expireDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signatur
        return  token;
    }
    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
            throw new RException("token 验证非法");
        }
        return jwt.getClaims();

    }

    /**
     * 根据Token获取user_id
     * 返回为空时候表示 token验证失败
     * @param token
     * @return user_id
     */
    public static String getAppUID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get("userId");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
        }

        if(user_id_claim!=null&&!StringUtils.isEmpty(user_id_claim.asString())){
            return user_id_claim.asString();
        }
        return  null;

    }
}
