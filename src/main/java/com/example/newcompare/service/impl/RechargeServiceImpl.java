package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newcompare.common.utils.AlipayUtil;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.Recharge;
import com.example.newcompare.entity.User;
import com.example.newcompare.mapper.RechargeMapper;
import com.example.newcompare.mapper.UserMapper;
import com.example.newcompare.service.RechargeService;
import com.example.newcompare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

@Service
public class RechargeServiceImpl extends ServiceImpl<RechargeMapper, Recharge> implements RechargeService {

    @Autowired
    RechargeMapper mapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

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


    /**
     * 获取对比订单信息
     */
    @Override
    public Recharge getDiffInformation(String id) {
        /*LambdaQueryWrapper<OrderLog> wrapper = new LambdaQueryWrapper<>();*/
        QueryWrapper<Recharge> queryWrapper = new QueryWrapper<>();
        /*QueryWrapper<OrderLog> w1=new QueryWrapper<>();*/
        queryWrapper.eq("out_trade_no", id);
        Recharge recharge = mapper.selectOne(queryWrapper);
        return recharge;
    }


    /**
     * 修改订单支付状态
     */

    @Override
    public boolean updateStatus(Recharge recharge) {
        UpdateWrapper<Recharge> wrapper=new UpdateWrapper<>();
        wrapper.eq("id", recharge.getId());
        wrapper.set("status","complete");
        int update = mapper.update(recharge, wrapper);
        return update == 1;
    }

    /**
     *        修改用户余额
     * @param recharge
     * @return
     */

    @Override
    public boolean addUserMoney(Recharge recharge) {
        User byId = userService.getById(recharge.getId());

        UpdateWrapper<User> wrapper=new UpdateWrapper<>();
        wrapper.eq("user_id",recharge.getId());
        wrapper.set("balance", Float.parseFloat(recharge.getFee()) + byId.getBalance());//1

        int update = userMapper.update(byId, wrapper);

        return update==1;
    }


}
