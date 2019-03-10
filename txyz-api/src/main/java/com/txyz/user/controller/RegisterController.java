package com.txyz.user.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.txyz.common.cache.redis.RedisUtils;
import com.txyz.common.constant.Cons;
import com.txyz.common.constant.ResponseCode;
import com.txyz.common.model.ReLocation;
import com.txyz.common.result.R;
import com.txyz.common.utils.LocationUtil;
import com.txyz.user.model.User;
import com.txyz.user.model.UserPharmacy;
import com.txyz.user.redisKey.PhoneRedisKey;
import com.txyz.user.request.PhoneRequest;
import com.txyz.user.request.RegistRequest;
import com.txyz.user.request.UserPharmacyRequest;
import com.txyz.user.service.UserPharmacyService;
import com.txyz.user.service.UserService;
import com.txyz.user.utils.PhoneCheckUtils;
import com.txyz.user.utils.SendMessageUtils;
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
@RequestMapping("reg")
public class RegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private SendMessageUtils sendMessageUtils;
    @Autowired
    private UserPharmacyService userPharmacyService;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 注册获取短信
     * @param phoneRequest
     * @return
     */
    @RequestMapping("getMessage")
    @ResponseBody
    public R getMessage(@RequestBody PhoneRequest phoneRequest){
        if(StringUtils.isEmpty(phoneRequest.getPhone())){
            return  R.error(ResponseCode.PARAM_ERROR,"手机号不能为空");
        }

        if(!PhoneCheckUtils.isPhoneLegal(phoneRequest.getPhone())){
            return R.error(ResponseCode.PARAM_ERROR,"手机号码不正确");
        }
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.where("user_account={0}",phoneRequest.getPhone());

        int num = userService.selectCount(wrapper);
        if(num>0){
            return R.error(ResponseCode.PARAM_ERROR,"手机号已存在");
        }
        String random = sendMessageUtils.sendMessage(phoneRequest.getPhone());
        if(StringUtils.isNotEmpty(random)){
            return R.ok().put("randomCode",random);
        }else {
            return R.error(ResponseCode.ERROR,"发送短信失败");
        }
    }

    /**
     * 注册第一步
     * @param registRequest
     * @return
     */
    @RequestMapping(value = "registFirstStep",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R registFirstStep(@RequestBody @Validated RegistRequest registRequest){
        if(!registRequest.getPassword().equals(registRequest.getRePassword())){
            return R.error(ResponseCode.ERROR,"两次密码不一致");
        }
        String random = redisUtils.get(PhoneRedisKey.getPhoneRandom(registRequest.getPhone()));
        if(StringUtils.isEmpty(random)||!registRequest.getRandomCode().equals(random)){
            return R.error(ResponseCode.ERROR,"验证码不存在或已过期");
        }
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.where("user_account={0}",registRequest.getPhone());

        int num = userService.selectCount(wrapper);
        if(num>0){
            return R.error(ResponseCode.PARAM_ERROR,"手机号已存在");
        }

        User user = new User();
        user.setAccount(registRequest.getPhone());
        user.setPassword(DigestUtils.md5Hex(registRequest.getPassword()));
        if(StringUtils.isNotEmpty(registRequest.getInvitCode())){
            user.setInvitCode(registRequest.getInvitCode());
        }
        userService.insert(user);
        EntityWrapper<User> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.setEntity(user);
       List<User> userList = userService.selectList(userEntityWrapper);
        redisUtils.del(PhoneRedisKey.getPhoneRandom(registRequest.getPhone()));
        return R.ok().put("userId",userList.get(0).getUserId());
    }

    /**
     * 注册自动获取药店地址
     * @return
     */
    @RequestMapping(value = "autoUserPharmacyAddress",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R autoUserPharmacyAddress(@RequestBody UserPharmacyRequest pharmacyRequest){
        if(StringUtils.isEmpty(pharmacyRequest.getLat())||StringUtils.isEmpty(pharmacyRequest.getLng())){
            return  R.error(ResponseCode.PARAM_ERROR,"必传参数不能为空");
        }
        List<ReLocation> locations= LocationUtil.getLocationData("药店",pharmacyRequest.getLat(),pharmacyRequest.getLng());
        if(locations!=null&&locations.size()>0){
            return R.ok().put("list",locations);
        }else {
            return R.noData();
        }
    }
    @RequestMapping(value = "autoRegistSecStep",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R  autoRegistSecStep(@RequestBody UserPharmacyRequest pharmacyRequest){
            if(StringUtils.isEmpty(pharmacyRequest.getLat())){
                return R.error(ResponseCode.PARAM_ERROR,"lat不能为空");
            }
        if(StringUtils.isEmpty(pharmacyRequest.getLng())){
            return R.error(ResponseCode.PARAM_ERROR,"lng不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getAddress())){
            return R.error(ResponseCode.PARAM_ERROR,"address不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getCertImg())){
            return R.error(ResponseCode.PARAM_ERROR,"certImg不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getName())){
            return R.error(ResponseCode.PARAM_ERROR,"name不能为空");
        }
        if(pharmacyRequest.getUserId()==null){
            return R.error(ResponseCode.PARAM_ERROR,"用户Id不能为空");
        }
       boolean flag =  userPharmacyService.checkUserPharmacyExist(pharmacyRequest.getUserId());
            if(flag){
                return R.error(ResponseCode.ERROR,"该用户已经绑定药店");
            }
            userPharmacyService.autoRegPharmacy(pharmacyRequest);
            return R.ok();
    }
    @RequestMapping(value = "inputRegistSecStep",consumes = Cons.JSON_UTF8,produces = Cons.JSON_UTF8)
    @ResponseBody
    public R  inputRegistSecStep(@RequestBody UserPharmacyRequest pharmacyRequest){
        if(StringUtils.isEmpty(pharmacyRequest.getProviceCode())){
            return R.error(ResponseCode.PARAM_ERROR,"proviceCode不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getProviceName())){
            return R.error(ResponseCode.PARAM_ERROR,"proviceName不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getCityCode())){
            return R.error(ResponseCode.PARAM_ERROR,"cityCode不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getCityName())){
            return R.error(ResponseCode.PARAM_ERROR,"cityName不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getAreaCode())){
            return R.error(ResponseCode.PARAM_ERROR,"areaCode不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getAreaName())){
            return R.error(ResponseCode.PARAM_ERROR,"areaName不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getAddress())){
            return R.error(ResponseCode.PARAM_ERROR,"address不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getCertImg())){
            return R.error(ResponseCode.PARAM_ERROR,"certImg不能为空");
        }
        if(StringUtils.isEmpty(pharmacyRequest.getName())){
            return R.error(ResponseCode.PARAM_ERROR,"name不能为空");
        }
        if(pharmacyRequest.getUserId()==null){
            return R.error(ResponseCode.PARAM_ERROR,"用户Id不能为空");
        }
        boolean flag =  userPharmacyService.checkUserPharmacyExist(pharmacyRequest.getUserId());
        if(flag){
            return R.error(ResponseCode.ERROR,"该用户已经绑定药店");
        }
        userPharmacyService.autoRegPharmacy(pharmacyRequest);
        return R.ok();
    }
}
