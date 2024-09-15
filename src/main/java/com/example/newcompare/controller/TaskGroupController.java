package com.example.newcompare.controller;


import com.example.newcompare.service.OrderLogService;
import com.example.newcompare.service.TaskGroupService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taskGroup")
public class TaskGroupController {


    @Autowired
    private TaskGroupService taskGroupService;

    @Autowired
    private OrderLogService orderLogService;


    @GetMapping("/getGroups")
    public void getGroups() {
//        this.taskGroupService.page()
    }

    @PostMapping("/create")
    public Result createGroup(@Param("name") String name) {
        return null;
    }
}
