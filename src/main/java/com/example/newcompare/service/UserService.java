package com.example.newcompare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.User;

import java.math.BigDecimal;

public interface UserService extends IService<User> {
    Float getBalance(Integer id);

    void updataUser(User user);

    User getUserById(Integer id);
}
