package com.example.newcompare.mapper;

import com.example.newcompare.entity.OrderLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

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

    OrderLog getByWorkCode(@Param("workCode") String workCode);
}
