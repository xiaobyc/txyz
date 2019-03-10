package com.txyz.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.txyz.user.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao extends BaseMapper<User> {

    public void updateUserPassword(@Param("userId") Long userId, @Param("password") String password);
}
