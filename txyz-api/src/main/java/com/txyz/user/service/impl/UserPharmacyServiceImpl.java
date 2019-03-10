package com.txyz.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.txyz.user.dao.UserPharmacyDao;
import com.txyz.user.model.UserPharmacy;
import com.txyz.user.request.UserPharmacyRequest;
import com.txyz.user.service.UserPharmacyService;
import com.txyz.user.utils.PharmacyConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserPharmacyServiceImpl extends ServiceImpl<UserPharmacyDao,UserPharmacy> implements UserPharmacyService {
    @Override
    public boolean checkUserPharmacyExist(Long userId) {
        EntityWrapper<UserPharmacy> wrapper = new EntityWrapper<>();
        UserPharmacy  search = new UserPharmacy();
        search.setUserId(userId);
        int count = baseMapper.selectCount(wrapper);
        if(count>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean autoRegPharmacy(UserPharmacyRequest pharmacyRequest) {
        UserPharmacy pharmacy = new UserPharmacy();
        pharmacy.setAddress(pharmacyRequest.getAddress());
        pharmacy.setUserId(pharmacyRequest.getUserId());
        pharmacy.setCertImg(pharmacyRequest.getCertImg());
        pharmacy.setLat(pharmacyRequest.getLat());
        pharmacy.setLng(pharmacyRequest.getLng());
        pharmacy.setName(pharmacyRequest.getName());
        pharmacy.setType(PharmacyConstant.AUTO);
        if(StringUtils.isNotEmpty(pharmacyRequest.getAddressImg())){
            pharmacy.setAddressImg(pharmacyRequest.getAddressImg());
        }
        if(StringUtils.isNotEmpty(pharmacyRequest.getOtherImg())){
            pharmacy.setOtherImg(pharmacyRequest.getOtherImg());
        }
        baseMapper.insert(pharmacy);
        return true;
    }

    @Override
    public boolean inputRegPharmacy(UserPharmacyRequest pharmacyRequest) {
        UserPharmacy pharmacy = new UserPharmacy();
        pharmacy.setAddress(pharmacyRequest.getAddress());
        pharmacy.setUserId(pharmacyRequest.getUserId());
        pharmacy.setCertImg(pharmacyRequest.getCertImg());
        pharmacy.setName(pharmacyRequest.getName());
        pharmacy.setType(PharmacyConstant.INPUT);
        pharmacy.setProviceCode(pharmacyRequest.getProviceCode());
        pharmacy.setProviceName(pharmacyRequest.getProviceName());
        pharmacy.setCityCode(pharmacyRequest.getCityCode());
        pharmacy.setCityName(pharmacyRequest.getCityName());
        pharmacy.setAreaCode(pharmacyRequest.getAreaCode());
        pharmacy.setAreaName(pharmacyRequest.getAreaName());
        if(StringUtils.isNotEmpty(pharmacyRequest.getAddressImg())){
            pharmacy.setAddressImg(pharmacyRequest.getAddressImg());
        }
        if(StringUtils.isNotEmpty(pharmacyRequest.getOtherImg())){
            pharmacy.setOtherImg(pharmacyRequest.getOtherImg());
        }
        baseMapper.insert(pharmacy);
        return false;
    }

    @Override
    public boolean updateUserPharmacy(UserPharmacyRequest pharmacyRequest) {
        baseMapper.updateUserPharmacy(pharmacyRequest);
        return false;
    }
}
