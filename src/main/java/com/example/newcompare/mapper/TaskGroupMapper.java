package com.example.newcompare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.newcompare.entity.File;
import com.example.newcompare.entity.TaskGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskGroupMapper extends BaseMapper<TaskGroup> {

    Page<TaskGroup> getGroups(@Param("Page") Page<TaskGroup> Page, @Param("keyWords") String keyWords, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Page<TaskGroup> getGroup(@Param("Page") Page<TaskGroup> Page, @Param("groupId") String groupId);
}
