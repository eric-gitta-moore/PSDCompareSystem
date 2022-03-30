package com.example.compare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.compare.entity.OrderLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 支付订单表 服务类
 * </p>
 *
 * @author nosgua
 * @since 2022-03-26
 */

public interface OrderLogService extends IService<OrderLog>{
    /**
     * 根据订单号获取订单
     * @param outTradeId
     * @return
     */
    OrderLog getOrderLog(String outTradeId);

    /**
     * 插入新的订单
     * @return
     */
    OrderLog insertOrderLog();

    /**
     * 调用请求支付
     */

    String AlipayUtils(OrderLog orderLog);

    /**
     * 保存OrderLog信息
     * @param orderLog
     * @return
     */
    public Integer saveOrderLog(OrderLog orderLog);

    /**
     * 查询订单支付状态并且更新数据库
     * @param outTradeNo
     * @return
     */
    boolean checkOrderAndUpdateDatabase(String outTradeNo);

    /**
     *      查询订单信息
     */

    public OrderLog getDiffInformation(String id);

    /**
     * 修改支付状态
     */

    public boolean updateOrderStatus(OrderLog orderLog);


    String getCompareIdByOrderId(String id);
}
