package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newcompare.entity.User;
import com.example.newcompare.mapper.UserMapper;
import com.example.newcompare.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
