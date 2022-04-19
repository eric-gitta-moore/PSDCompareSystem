package com.example.newcompare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newcompare.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
