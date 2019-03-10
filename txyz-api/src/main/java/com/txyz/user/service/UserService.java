package com.txyz.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.txyz.user.model.User;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 登录
     * @param account
     * @return
     */
    public List<User> loginUserByAccountAndPassword(String account);

    /**
     * 根据用户ID修改用户密码
     * @param userId
     * @param password
     */
    public void updateUserPassword(Long userId,String password);
}
