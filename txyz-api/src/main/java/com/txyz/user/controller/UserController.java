package com.txyz.user.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.txyz.common.cache.redis.RedisUtils;
import com.txyz.common.constant.Cons;
import com.txyz.common.constant.ResponseCode;
import com.txyz.common.result.R;
import com.txyz.user.model.User;
import com.txyz.user.model.UserPharmacy;
import com.txyz.user.redisKey.PhoneRedisKey;
import com.txyz.user.request.PhoneRequest;
import com.txyz.user.request.UserPharmacyRequest;
import com.txyz.user.service.UserPharmacyService;
import com.txyz.user.service.UserService;
import com.txyz.user.utils.PhoneCheckUtils;
import com.txyz.user.utils.SendMessageUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("userInfo")
public class UserController {
    @Autowired
    private UserPharmacyService userPharmacyService;
    @Autowired
    private SendMessageUtils sendMessageUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;
    @RequestMapping(value = "updateUserPharmacry",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R updateUserPharmacry(@RequestBody UserPharmacyRequest pharmacyRequest){

        if(pharmacyRequest.getUserId()==null){
            return  R.error(ResponseCode.PARAM_ERROR,"用户ID不能为空");
        }
        EntityWrapper<UserPharmacy> wrapper = new EntityWrapper<>();
        userPharmacyService.updateUserPharmacy(pharmacyRequest);
        return R.ok();
    }
    @RequestMapping(value = "getMessageForPassword",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R getMessageForPassword(@RequestBody PhoneRequest phoneRequest){
        if(StringUtils.isEmpty(phoneRequest.getPhone())){
            return  R.error(ResponseCode.PARAM_ERROR,"手机号不能为空");
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
        String random = sendMessageUtils.sendMessage(phoneRequest.getPhone());
        if(StringUtils.isNotEmpty(random)){
            return R.ok().put("randomCode",random);
        }else {
            return R.error(ResponseCode.ERROR,"发送短信失败");
        }
    }
    @RequestMapping(value = "checkMessageForPassword",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R checkMessageForPassword(@RequestBody PhoneRequest phoneRequest){
        if(StringUtils.isEmpty(phoneRequest.getPhone())){
            return  R.error(ResponseCode.PARAM_ERROR,"手机号不能为空");
        }

        if(!PhoneCheckUtils.isPhoneLegal(phoneRequest.getPhone())){
            return R.error(ResponseCode.PARAM_ERROR,"手机号码不正确");
        }

        String random = redisUtils.get(PhoneRedisKey.getPhoneRandom(phoneRequest.getPhone()));
        if(StringUtils.isEmpty(random)||!phoneRequest.getRandomCode().equals(random)){
            return R.error(ResponseCode.ERROR,"验证码不存在或已过期");
        }

       List<User> list  =  userService.loginUserByAccountAndPassword(phoneRequest.getPhone());
        if(list!=null&&list.size()>0){
            User user = list.get(0);
            return  R.ok().put("userId",user.getUserId());
        }else {
         return R.noData();
        }
    }
    @RequestMapping(value = "resetPassword",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R resetPassword(@RequestBody PhoneRequest phoneRequest){
        if(phoneRequest.getUserId()==null){
            return  R.error(ResponseCode.PARAM_ERROR,"用户ID不能为空");
        }

        if(StringUtils.isEmpty(phoneRequest.getPassword())||StringUtils.isEmpty(phoneRequest.getRePassword())){
            return  R.error(ResponseCode.PARAM_ERROR,"password 或rePassword不能为空");
        }

        if(!phoneRequest.getPassword().equals(phoneRequest.getRePassword())){
            return  R.error(ResponseCode.PARAM_ERROR,"password 和rePassword不一致");
        }
        userService.updateUserPassword(phoneRequest.getUserId(),DigestUtils.md5Hex(phoneRequest.getPassword()));
            return  R.ok();
    }
}
