package com.example.newcompare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newcompare.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
