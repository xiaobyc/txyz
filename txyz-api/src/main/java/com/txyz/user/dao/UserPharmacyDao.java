package com.txyz.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.txyz.user.model.UserPharmacy;
import com.txyz.user.request.UserPharmacyRequest;
import org.apache.ibatis.annotations.Param;

public interface UserPharmacyDao extends BaseMapper<UserPharmacy> {

    public void updateUserPharmacy(@Param("pharmacy")UserPharmacyRequest pharmacyRequest);
}
