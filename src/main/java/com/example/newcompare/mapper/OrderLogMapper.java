package com.example.newcompare.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.newcompare.entity.File;
import com.example.newcompare.entity.OrderLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 支付订单表 Mapper 接口
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@Mapper
public interface OrderLogMapper extends BaseMapper<OrderLog> {

    int allDelete(@Param("Ids") String[] Ids);


    /**
     * 通过taskID查出存在file表中对比前的两张图片的信息
     * @param tsakId
     * @return
     */
    List<File> getFilecodeByTaskId(@Param("taskId") Integer tsakId);

}
