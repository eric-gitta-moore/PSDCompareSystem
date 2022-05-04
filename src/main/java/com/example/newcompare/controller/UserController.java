package com.example.newcompare.controller;


import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.User;
import com.example.newcompare.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@Api(tags = "user")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;


    @ApiOperation("徐启峰====>获取user余额信息")
    @GetMapping("/getUser")
    public Result getuser(){
        User user = userService.getOne(null);
        if(user==null)
            return Result.fail(400,"获取用户余额失败！",new HashMap<String,String>());

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("balance", String.valueOf(user.getBalance()));
        return Result.success(200,"获取用户余额成功！",hashMap);
    }
}
