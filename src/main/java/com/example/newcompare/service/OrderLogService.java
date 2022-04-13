package com.example.newcompare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.newcompare.entity.File;
import com.example.newcompare.entity.OrderLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 支付订单表 服务类
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
public interface OrderLogService extends IService<OrderLog> {

    OrderLog getOrderLog(String outTradeId);

    String useAlipayUtils(OrderLog orderLog);

    boolean checkOrderAndUpdateDatabase(String outTradeNo);


    int allDelete(String[] Ids);
    int delete(String[] serialNumbers);

    /**
     * 保存数据进file表
     * @param orderLog
     * @return
     */
    public Boolean insertOrderLog(OrderLog orderLog);

    /**
     * 根据ID查询file表中的数据
     * @param orderId
     * @return
     */
    public OrderLog selectById(Integer orderId);

    /**
     * 根据taskID查询task_group表中的工作码
     * @param taskId
     * @return
     */
    public String getWorkCodeByTaskId(Integer taskId);

    /**
     * 根据taskID查询file表中的文件码
     * @param taskId
     * @return
     */
    public List<File> getFilecodeByTaskId(Integer taskId);

}
