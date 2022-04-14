package com.example.newcompare.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.TaskGroup;

public interface TaskGroupService extends IService<TaskGroup> {

    TaskGroup selectById(Integer taskId);

    Integer create(TaskGroup taskGroup);

    Page<TaskGroup> getGroups(Page<TaskGroup> Page, String keyWords, String startTime, String endTime);

    Page<OrderLog> getGroupById(Page<OrderLog> page, String groupId);

    String getWorkCodeByTaskId(Integer id);
}
