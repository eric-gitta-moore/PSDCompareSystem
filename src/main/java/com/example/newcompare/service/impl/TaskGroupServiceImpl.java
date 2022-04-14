package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.TaskGroup;
import com.example.newcompare.mapper.TaskGroupMapper;
import com.example.newcompare.service.TaskGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.newcompare.entity.WorkCode;
import com.example.newcompare.mapper.TaskGroupMapper;
import com.example.newcompare.service.TaskGroupService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;




@Service
public class TaskGroupServiceImpl extends ServiceImpl<TaskGroupMapper, TaskGroup> implements TaskGroupService {

    @Autowired
    private TaskGroupMapper taskGroupMapper;

    @Resource
    TaskGroupMapper mapper;

    @Override
    public Integer create(TaskGroup taskGroup) {
        return mapper.insert(taskGroup);
    }

    @Override
    public Page<TaskGroup> getGroups(Page<TaskGroup> Page, String keyWords, String startTime, String endTime) {
        return mapper.getGroups(Page, keyWords, startTime, endTime);
    }

    @Override
    public Page<OrderLog> getGroupById(Page<OrderLog> Page, String groupId) {
        return mapper.getGroup(Page, groupId);
    }

    @Override
    public TaskGroup selectById(Integer taskId) {
        return taskGroupMapper.selectById(taskId);


    }
}
