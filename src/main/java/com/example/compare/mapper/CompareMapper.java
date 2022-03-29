package com.example.compare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.compare.entity.Compare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompareMapper extends BaseMapper<Compare> {
    String getOrderIdByid(Integer id);

    int allDelete(int orderId);

    List<Compare> search(String keywords, String startTime, String endTime, int index, int maxPage);

    Compare searchOne(@Param("compareId")Integer compareId);

    Compare bySerialNumber(String serialNumber);

    List<Compare> allSelect();
}
