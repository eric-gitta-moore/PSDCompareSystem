package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newcompare.entity.Recharge;
import com.example.newcompare.mapper.RechargeMapper;
import com.example.newcompare.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechargeServiceImpl extends ServiceImpl<RechargeMapper, Recharge> implements RechargeService {
    @Autowired
    RechargeMapper mapper;
    @Override
    public int insert(Recharge recharge) {
        return mapper.insert(recharge);
    }
}
