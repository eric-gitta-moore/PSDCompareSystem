package com.example.compare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.compare.entity.Compare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompareMapper extends BaseMapper<Compare> {
    String getOrderIdByid(Integer id);

    int allDelete(int orderId);

    Page<Compare> search(@Param("Page") Page<Compare> Page, @Param("keyWords") String keyWords, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Compare searchOne(@Param("compareId") Integer compareId);

    Compare bySerialNumber(String serialNumber);

    List<Compare> allSelect();

    String getTotalRows();
}
