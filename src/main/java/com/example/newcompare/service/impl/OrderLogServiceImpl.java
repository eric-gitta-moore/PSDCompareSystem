package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.newcompare.common.utils.AlipayUtil;
import com.example.newcompare.entity.File;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.TaskGroup;
import com.example.newcompare.mapper.OrderLogMapper;
import com.example.newcompare.mapper.TaskGroupMapper;
import com.example.newcompare.service.OrderLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    AlipayUtil alipayUtil;

    @Autowired
    OrderLogMapper mapper;

    @Autowired
    private TaskGroupMapper taskGroupMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Override
    public OrderLog getOrderLog(String outTradeId) {
        OrderLog orderLog = mapper.selectOne(new QueryWrapper<OrderLog>().eq("out_trade_id",outTradeId));
        return orderLog;
    }


    @Override
    public String useAlipayUtils(OrderLog orderLog) {
        String form = alipayUtil.pay(orderLog);
        return form;
    }

    /**
     * 查询订单支付状态并且更新数据库
     *
     * @param outTradeNo
     * @return
     */
    @Override
    public boolean checkOrderAndUpdateDatabase(String outTradeNo) {
        //判断数据库里面的支付状态
        OrderLog orderLog = this.getOrderLog(outTradeNo);
        if (orderLog.getStatus().equals("unpaid")) {
            //查单
            String status = alipayUtil.queryTradeStatus(outTradeNo);
            if (status.equals("TRADE_SUCCESS")) {
                //支付宝那边支付成功
                orderLog.setStatus("complete");
                return mapper.updateById(orderLog) > 0;
            }
        } else if (orderLog.getStatus().equals("complete")) {
            return true;
        }
        return false;
    }

    @Override
    public int allDelete(String[] Ids) {
        return mapper.allDelete(Ids);
    }

    @Override
    public int delete(String[] serialNumbers) {
        return 0;
    }

    @Override
    public Boolean insertOrderLog(OrderLog orderLog) {
        return mapper.insert(orderLog) > 0;
    }

    @Override
    public OrderLog selectById(Integer orderId) {
        OrderLog orderLog = mapper.selectById(orderId);
        return orderLog;
    }

    @Override
    public String getWorkCodeByTaskId(Integer taskId) {
        TaskGroup taskGroup = taskGroupMapper.selectById(taskId);
        String workCode = taskGroup.getWorkCode();
        return workCode;
    }

    @Override
    public List<File> getFilecodeByTaskId(Integer taskId) {
        List<File> files = orderLogMapper.getFilecodeByTaskId(taskId);
        return files;
    }
}
