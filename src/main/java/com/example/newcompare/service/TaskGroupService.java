package com.example.newcompare.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.TaskGroup;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.TaskGroup;
import org.springframework.stereotype.Service;

public interface TaskGroupService extends IService<TaskGroup> {

    public TaskGroup selectById(Integer taskId);

    Integer create(TaskGroup taskGroup);

    Page<TaskGroup> getGroups(Page<TaskGroup> Page, String keyWords, String startTime, String endTime);

    Page<TaskGroup> getGroupById(Page<TaskGroup> page, String groupId);

}
