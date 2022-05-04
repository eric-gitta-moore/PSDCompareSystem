package com.example.newcompare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.Recharge;

public interface RechargeService extends IService<Recharge> {

    int insert(Recharge recharge);

    Recharge getRecharge(String outTradeId);

    String useAlipayUtils(Recharge recharge);

    boolean checkOrderAndUpdateDatabase(String outTradeNo);
    Recharge getRechargeById(Integer id);

    Recharge getDiffInformation(String out_trade_no);

    boolean updateStatus(Recharge recharge);

    boolean addUserMoney(Recharge recharge);
}
