package com.example.newcompare.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.TaskGroup;
import com.example.newcompare.service.TaskGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@RestController
@Api(value = "taskGroup")
@RequestMapping("/taskGroup")
public class TaskGroupController {

    @Autowired
    TaskGroupService service;

    @PostMapping("/create")
    @ApiOperation("刘锦堂===》创建新的group")
    public Result create(@RequestBody TaskGroup group) {
        group.setName(group.getName());
        group.setCreateTime(LocalDateTime.now());
        Integer i = service.create(group);
        if (i == 0) {
            return Result.fail("插入失败");
        }
        return Result.success("插入成功");
    }

    @PostMapping("/getGroups")
    @ApiOperation(value = "郑前===》获取历史记录,keywords: 任务组名字or订单流水号or订单id" +
            "maxPage: 每页显示最大数量，" +
            "startPage: 开始页码,startTime和endTime: 要查询的时间段")
    public Result getGroups(@RequestBody Map<String, String> map) {
        //最大显示数量默认是10
        int maxPage = 10;
        //起始页码默认为是1
        int startPage = 1;
        String mPage = map.get("maxPage");
        String sPage = map.get("startPage");
        String keyWords = map.get("keyWords");
        String startTime = map.get("startTime");
        String endTime = map.get("endTime");
        if (sPage != null) {
            startPage = Integer.parseInt(sPage);
        }
        if (mPage != null) {
            maxPage = Integer.parseInt(mPage);
        }
        Page<TaskGroup> Page = new Page(startPage, maxPage);
        return Result.success(service.getGroups(Page, keyWords, startTime, endTime));
    }

    @GetMapping("/getGroupById")
    @ApiOperation(value = "郑前===》获取单个任务组")
    public Result getGroupById(String groupId) {
        //最大显示数量默认是10
        int maxPage = 10;
        //起始页码默认为是1
        int startPage = 1;
        Page<OrderLog> Page = new Page(startPage, maxPage);
        return Result.success(service.getGroupById(Page, groupId));
    }

    @ApiOperation("左呈祥===》批量删除group")
    @DeleteMapping("/delete")
    public Result deleteGroups(@RequestBody ArrayList<Integer> groupIds) {
        return service.remove(new LambdaQueryWrapper<TaskGroup>().in(TaskGroup::getId,groupIds)) ? Result.success(200,"删除成功","") : Result.fail(404,"删除失败","");
    }
}
