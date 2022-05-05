package com.example.newcompare.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.TaskGroup;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TaskGroupService extends IService<TaskGroup> {

    TaskGroup selectById(Integer taskId);

    Integer create(TaskGroup taskGroup);

    List<TaskGroup> getTotal( String keyWords, String startTime, String endTime);

    Page<TaskGroup> getGroups(Page<TaskGroup> Page, String keyWords, String startTime, String endTime, String defaultSort);

    Page<OrderLog> getGroupById(Page<OrderLog> page, String groupId);

    String getWorkCodeByTaskId(Integer id);

    List<TaskGroup> getAllIdFromTask(Integer[] id);

    List<OrderLog> getALlIdByTask(List<TaskGroup> list);

    boolean backZip(List<OrderLog> list,String path) throws Exception;
}
