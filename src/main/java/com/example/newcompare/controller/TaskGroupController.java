package com.example.newcompare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.TaskGroup;
import com.example.newcompare.service.TaskGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/taskGroup")
public class TaskGroupController {

    @Autowired
    TaskGroupService service;

    @PostMapping("/create")
    @ApiOperation("创建新的group")
    public Result create(@RequestBody TaskGroup group){
        group.setName(group.getName());
        group.setWorkCode(UUID.randomUUID().toString());
        group.setCreateTime(LocalDateTime.now());
        Integer i = service.create(group);
        if(i==0){
            return Result.fail("插入失败");
        }
        return Result.success("插入成功");
    }

    @PostMapping("/getGroups")
    @ApiOperation(value = "获取历史记录,keywords: 任务组名字或者订单流水号" +
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
        Page<TaskGroup> Page = new Page(startPage,maxPage);
        return Result.success(service.getGroups(Page, keyWords, startTime, endTime));
    }

    @PostMapping("/getGroupById")
    @ApiOperation(value = "获取单个任务组")
    public Result getGroupById(@RequestParam("groupId") String groupId) {
        //最大显示数量默认是10
        int maxPage = 10;
        //起始页码默认为是1
        int startPage = 1;
        Page<TaskGroup> Page = new Page(startPage,maxPage);
        return Result.success(service.getGroupById(Page,groupId));
    }
}
