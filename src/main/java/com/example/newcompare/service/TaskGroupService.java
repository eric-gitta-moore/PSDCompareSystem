package com.example.newcompare.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.TaskGroup;
import org.springframework.stereotype.Service;


public interface TaskGroupService extends IService<TaskGroup> {

    Integer create(TaskGroup taskGroup);

    Page<TaskGroup> getHistory(Page<TaskGroup> Page, String keyWords, String startTime, String endTime);

}
