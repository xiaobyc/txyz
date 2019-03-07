package com.txyz.user.controller;

import com.txyz.common.constant.ResponseCode;
import com.txyz.common.exception.RException;
import com.txyz.common.jwt.JwtToken;
import com.txyz.common.result.R;
import com.txyz.user.model.User;
import com.txyz.user.request.LoginRequest;
import com.txyz.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "user")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录接口 登录后返回用户信息包括生成token
     * @param request
     * @return
     */
    @RequestMapping("userLogin")
    @ResponseBody
    public R login(@RequestBody @Validated LoginRequest request){
           List<User> userList= userService.loginUserByAccountAndPassword(request.getUserName());
           if(userList==null){
               return R.error(ResponseCode.ACCOUNT_NOT_EXIST,"账号不存在");
           }
           User user = userList.get(0);
           if(!user.getPassword().endsWith(DigestUtils.md5Hex(request.getPassword()))){
               return R.error(ResponseCode.PASSWORD_ERROR,"密码错误");
           }else {
               try {
                   String token = JwtToken.createToken(user.getAccount());
                   return R.ok().put("user",user).put("token",token);
               } catch (Exception e) {
                   new RException(ResponseCode.ERROR,"生成token异常");
               }
               return R.error();
        }
    }
}
