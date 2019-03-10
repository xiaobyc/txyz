package com.txyz.user.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.txyz.common.cache.redis.RedisUtils;
import com.txyz.common.constant.Cons;
import com.txyz.common.constant.ResponseCode;
import com.txyz.common.exception.RException;
import com.txyz.common.jwt.JwtToken;
import com.txyz.common.result.R;
import com.txyz.user.model.User;
import com.txyz.user.redisKey.PhoneRedisKey;
import com.txyz.user.request.LoginRequest;
import com.txyz.user.request.PhoneRequest;
import com.txyz.user.request.RegistRequest;
import com.txyz.user.service.UserPharmacyService;
import com.txyz.user.service.UserService;
import com.txyz.user.utils.PhoneCheckUtils;
import com.txyz.user.utils.SendMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private SendMessageUtils sendMessageUtils;
    @Autowired
    private UserPharmacyService userPharmacyService;
    @Autowired
    private RedisUtils redisUtils;

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
                   boolean flag =userPharmacyService.checkUserPharmacyExist(user.getUserId());
                   if(flag){
                       //药店已经选择
                       user.setUserStep("1");
                   }else {
                       user.setUserStep("0");
                   }
                   return R.ok().put("user",user).put("token",token);
               } catch (Exception e) {
                   new RException(ResponseCode.ERROR,"生成token异常");
               }
               return R.error();
        }
    }

    @RequestMapping(value = "freeLoginMessage",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R freeLoginMessage(@RequestBody PhoneRequest phoneRequest){
        if(StringUtils.isEmpty(phoneRequest.getPhone())){
            return  R.error(ResponseCode.ERROR,"手机号不能为空");
        }

        if(!PhoneCheckUtils.isPhoneLegal(phoneRequest.getPhone())){
            return R.error(ResponseCode.PARAM_ERROR,"手机号码不正确");
        }

        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.where("user_account={0}",phoneRequest.getPhone());

        int num = userService.selectCount(wrapper);
        if(num==0){
            return R.error(ResponseCode.PARAM_ERROR,"手机号不存在");
        }

      String random =  sendMessageUtils.sendMessage(phoneRequest.getPhone());
        if(StringUtils.isNotEmpty(random)){
            return R.ok().put("randomCode",random);
        }else {
            return R.error(ResponseCode.ERROR,"发送短信失败");
        }
    }

    @RequestMapping(value = "freeLogin",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R freeLogin(@RequestBody PhoneRequest phoneRequest){
        if(StringUtils.isEmpty(phoneRequest.getPhone())||StringUtils.isEmpty(phoneRequest.getRandomCode())){
            return R.error(ResponseCode.PARAM_ERROR,"手机号或验证码不能为空");
        }
        String random = redisUtils.get(PhoneRedisKey.getPhoneRandom(phoneRequest.getPhone()));
        if(StringUtils.isEmpty(random)||!phoneRequest.getRandomCode().equals(random)){
            return R.error(ResponseCode.ERROR,"验证码不存在或已过期");
        }
       List<User> list = userService.loginUserByAccountAndPassword(phoneRequest.getPhone());
        if(list!=null&&list.size()>0){
            User user = list.get(0);
            try {
                String token = JwtToken.createToken(user.getAccount());
                boolean flag =userPharmacyService.checkUserPharmacyExist(user.getUserId());
                if(flag){
                    //药店已经选择
                    user.setUserStep("1");
                }else {
                    user.setUserStep("0");
                }
                return R.ok().put("user",user).put("token",token);
            } catch (Exception e) {
                new RException(ResponseCode.ERROR,"生成token异常");
            }
            return R.error();
        }else {
            return R.error();
        }
    }




}
