package com.example.newcompare.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.newcompare.entity.OrderLog;
import com.baomidou.mybatisplus.extension.service.IService;

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

    Page<OrderLog> getHistory(Page<OrderLog> Page, String keyWords, String startTime, String endTime);

    int allDelete(String[] serialNumbers);


    OrderLog getDiffInformation(String out_trade_no);

    boolean updateStatus(OrderLog out_trade_no);
}
