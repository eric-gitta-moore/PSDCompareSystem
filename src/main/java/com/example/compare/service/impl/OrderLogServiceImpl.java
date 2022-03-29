package com.example.compare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.compare.common.utils.AlipayUtil;
import com.example.compare.common.utils.Result;
import com.example.compare.entity.Compare;
import com.example.compare.entity.OrderLog;
import com.example.compare.mapper.OrderLogMapper;
import com.example.compare.service.OrderLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 支付订单表 服务实现类
 * </p>
 *
 * @author nosgua
 * @since 2022-03-26
 */
@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper,OrderLog> implements OrderLogService {
    @Autowired
    OrderLogMapper mapper;

    @Autowired
    AlipayUtil alipayUtil;

    @Override
    public OrderLog getOrderLog(String outTradeId) {
        OrderLog orderLog = mapper.selectOne(new QueryWrapper<OrderLog>().eq("out_trade_id",outTradeId));
        return orderLog;
    }

    @Override
    public OrderLog insertOrderLog() {
        OrderLog orderLog = new OrderLog();
        orderLog.setOutTradeId(UUID.randomUUID().toString());
        orderLog.setStatus("unpaid");
        orderLog.setTitle("test");
        orderLog.setFee(new BigDecimal(100));
        mapper.insert(orderLog);
        return orderLog;
    }

    @Override
    public String AlipayUtils(OrderLog orderLog) {
        String form = alipayUtil.pay(orderLog);
        return form;
    }




    /**
     * 查询订单数据
     * @param id
     * @return
     */
    @Override
    public OrderLog getDiffInformation(String id) {
        LambdaQueryWrapper<OrderLog> wrapper = new LambdaQueryWrapper<>();
        /*QueryWrapper<OrderLog> w1=new QueryWrapper<>();*/
          wrapper.eq(OrderLog::getOutTradeId,id);
        OrderLog orderLog = mapper.selectOne(wrapper);
        return orderLog;
    }

    @Override
    public boolean updateOrderStatus(OrderLog orderLog) {
        /*UpdateWrapper<OrderLog> q1=new UpdateWrapper<>();
        q1.eq("outTradeId",orderLog.getOutTradeId());*/
        LambdaUpdateWrapper<OrderLog> wrapper = new LambdaUpdateWrapper<>();
        /*QueryWrapper<OrderLog> w1=new QueryWrapper<>();*/
        wrapper.eq(OrderLog::getOutTradeId,orderLog.getOutTradeId());
        wrapper.set(OrderLog::getStatus,"complete");
        int update = mapper.update(orderLog, wrapper);
        if(update==1){
            return true;
        }

        return false;
    }

    @Override
    public String getCompareIdByOrderId(String id) {
        return mapper.getCompareIdByOrderId(id);
    }

    @Override
    public Integer saveOrderLog(OrderLog orderLog) {
        mapper.insert(orderLog);
        Integer id = orderLog.getId();
        return id;
    }
}
