package com.txyz.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.txyz.user.model.UserPharmacy;
import com.txyz.user.request.UserPharmacyRequest;

public interface UserPharmacyService extends IService<UserPharmacy> {
    /**
     * 检查用户注册到哪一步
     * @param userId
     * @return
     */
     public boolean checkUserPharmacyExist(Long userId);

    /**
     * 自动注册时候保存药店
     * @param pharmacyRequest
     * @return
     */
     public boolean autoRegPharmacy(UserPharmacyRequest pharmacyRequest);

    /**
     * 自己输入保存药店信息
     * @param pharmacyRequest
     * @return
     */
     public boolean inputRegPharmacy(UserPharmacyRequest pharmacyRequest);

    /**
     * 根据用户ID 修改用户证件照
     * @param pharmacyRequest
     * @return
     */
     public boolean updateUserPharmacy(UserPharmacyRequest pharmacyRequest);
}
