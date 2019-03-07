package com.txyz.user.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.txyz.user.dao.UserDao;
import com.txyz.user.model.User;
import com.txyz.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {
    @Override
    public List<User> loginUserByAccountAndPassword(String account) {
        User user = new User();
        user.setAccount(account);
        //user.setPassword(DigestUtils.md5Hex(password));
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.setEntity(user);
        return baseMapper.selectList(wrapper);
    }
}
