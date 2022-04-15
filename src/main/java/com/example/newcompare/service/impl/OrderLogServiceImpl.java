package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.newcompare.common.utils.AlipayUtil;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.mapper.OrderLogMapper;
import com.example.newcompare.service.OrderLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 支付订单表 服务实现类
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper, OrderLog> implements OrderLogService {


    @Autowired
    private OrderLogMapper mapper;


    @Override
    public OrderLog getByWorkCode(String workCode) {
        return mapper.getByWorkCode(workCode);
    }

    @Override
    public void insertOrderLog(OrderLog orderLog) {

    }


    /**
     * 获取对比订单信息
     */
    @Override
    public OrderLog getDiffInformation(String id) {
        /*LambdaQueryWrapper<OrderLog> wrapper = new LambdaQueryWrapper<>();*/
        QueryWrapper<OrderLog> queryWrapper = new QueryWrapper<>();
        /*QueryWrapper<OrderLog> w1=new QueryWrapper<>();*/
        queryWrapper.eq("out_trade_id", id);
        OrderLog orderLog = mapper.selectOne(queryWrapper);
        System.out.println(orderLog);
        return orderLog;
    }


    /**
     * 修改订单支付状态
     */

    @Override
    public boolean updateStatus(OrderLog orderLog) {
        LambdaUpdateWrapper<OrderLog> wrapper = new LambdaUpdateWrapper<>();
//        wrapper.eq(OrderLog::getOutTradeId, orderLog.getOutTradeId());
        wrapper.set(OrderLog::getStatus, "finish");
        int update = mapper.update(orderLog, wrapper);
        return update == 1;
    }



    @Override
    public int orderDelete(String[] Ids) {
        List<OrderLog> orderLogs = null;
        //防止Ids为空时造成异常
        if (Ids.length > 0) {
            orderLogs = mapper.selectBatchIds(Arrays.asList(Ids));
        }
        //当Ids对应的订单处于删除状态，则不执行SQL语句，反之执行
        if(orderLogs!=null&&orderLogs.size()>0) {
            return mapper.orderDelete(Ids);
        }
        else {
            return 0;
        }
    }
}
