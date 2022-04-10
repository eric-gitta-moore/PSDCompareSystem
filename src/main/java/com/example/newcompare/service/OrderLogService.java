package com.example.newcompare.service;

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

    boolean checkOrderAndUpdateDatabase(String outTradeNo);
}
