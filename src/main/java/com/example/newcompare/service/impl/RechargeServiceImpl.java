package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newcompare.common.utils.AlipayUtil;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.Recharge;
import com.example.newcompare.mapper.RechargeMapper;
import com.example.newcompare.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechargeServiceImpl extends ServiceImpl<RechargeMapper, Recharge> implements RechargeService {

    @Autowired
    RechargeMapper mapper;

    @Autowired
    private AlipayUtil alipayUtil;

    @Override
    public int insert(Recharge recharge) {
        return mapper.insert(recharge);
    }

    @Override
    public Recharge getRecharge(String outTradeId) {
        Recharge recharge = mapper.queryRecharge(outTradeId);
        return recharge;

    }
    @Override
    public String useAlipayUtils(Recharge recharge) {
        return alipayUtil.pay(recharge);
    }


    /**
     * 查询订单支付状态并且更新数据库
     *
     * @param
     */
    @Override
    public boolean checkOrderAndUpdateDatabase(String outTradeNo) {
        //判断数据库里面的支付状态
        Recharge recharge = mapper.queryRecharge(outTradeNo);
        if (recharge.getStatus().equals("unpaid")) {
            //查单
            String status = alipayUtil.queryTradeStatus(outTradeNo);
            if (status.equals("TRADE_SUCCESS")) {
                //支付宝那边支付成功
                recharge.setStatus("complete");
                return mapper.updateById(recharge) > 0;
            }
        } else {
            return recharge.getStatus().equals("complete");
        }
        return false;
    }

}
