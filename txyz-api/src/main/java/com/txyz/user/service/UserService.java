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
}
