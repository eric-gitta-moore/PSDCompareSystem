package com.example.newcompare.controller;

import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.TaskGroup;
import com.example.newcompare.service.TaskGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/taskGroup")
public class TaskGroupController {

    @Autowired
    TaskGroupService service;

    @PostMapping("/create")
    public Result create(String name){
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setName(name);
        taskGroup.setCreateTime(LocalDateTime.now());
        taskGroup.setWorkCode(UUID.randomUUID().toString());
        Integer i = service.create(taskGroup);
        if(i==0){
            return Result.fail("插入失败");
        }
        return Result.success("插入成功");
    }
}
