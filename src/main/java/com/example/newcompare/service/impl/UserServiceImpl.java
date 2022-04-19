package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newcompare.entity.User;
import com.example.newcompare.mapper.UserMapper;
import com.example.newcompare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Float getBalance(Integer id) {
        User user = userMapper.selectById(id);
        return user.getBalance();
    }

    @Override
    public void updataUser(User user) {
        userMapper.update(user,null);
    }


}
