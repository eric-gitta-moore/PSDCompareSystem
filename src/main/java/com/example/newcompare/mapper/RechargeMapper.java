package com.example.newcompare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newcompare.entity.Recharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RechargeMapper extends BaseMapper<Recharge> {

    Recharge queryRecharge(@Param("oid") String outTradeId);
    Recharge getById(@Param("id") Integer id);
}
