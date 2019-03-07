package com.txyz.common.filter;


import com.txyz.common.anno.PassToken;
import com.txyz.common.anno.UserLoginToken;
import com.txyz.common.constant.ResponseCode;
import com.txyz.common.exception.RException;
import com.txyz.common.jwt.JwtToken;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 对接口进行验证token
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是执行的方法 跳过验证
        if(!(handler instanceof HandlerMethod)){
            return  true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        //检查是否有不需要验证token 有跳过认证
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        //检查有没有需要登录认证的
        if(method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if(userLoginToken.required()){
                //执行认证
                if(token==null){
                    throw new RException("无token,请重新登录",ResponseCode.TOKEN_NEED);
                }
                //获取token中的用户ID 验证token是否过期
                String userId = JwtToken.getAppUID(token);
                if(StringUtils.isEmpty(userId)){
                    throw new RException("token过期,请重新获取",ResponseCode.TOKEN_EXPIRE);
                }
                return true;
            }
        }
        return true;
    }
}
