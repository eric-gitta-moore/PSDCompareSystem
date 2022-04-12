package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newcompare.entity.TaskGroup;
import com.example.newcompare.entity.WorkCode;
import com.example.newcompare.mapper.TaskGroupMapper;
import com.example.newcompare.service.TaskGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class TaskGroupServiceImpl extends ServiceImpl<TaskGroupMapper, TaskGroup> implements TaskGroupService {
    @Resource
    TaskGroupMapper mapper;
    @Override
    public Integer create(TaskGroup taskGroup) {
        return mapper.insert(taskGroup);
    }
}
