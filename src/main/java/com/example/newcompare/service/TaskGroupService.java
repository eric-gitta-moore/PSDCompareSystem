package com.example.newcompare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.TaskGroup;
import com.example.newcompare.entity.WorkCode;
import org.springframework.stereotype.Service;

@Service
public interface TaskGroupService extends IService<TaskGroup> {
    Integer create(TaskGroup taskGroup);
}
