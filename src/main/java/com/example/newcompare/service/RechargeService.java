package com.example.newcompare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newcompare.entity.Recharge;

public interface RechargeService extends IService<Recharge> {
    int insert(Recharge recharge);
}
