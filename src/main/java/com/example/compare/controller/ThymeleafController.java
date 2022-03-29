package com.example.compare.controller;

import com.example.compare.entity.OrderLog;
import com.example.compare.service.OrderLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(value = "ThymeleafController",tags = "thymeleaf")
public class ThymeleafController {
    @Autowired
    OrderLogService service;
    /**
     * 跳转到收银台
     * @param outTradeId  订单编号
     * @param model
     * @return
     */
    @ApiOperation(value = "跳转登入界面， outTradeId: 订单编号")
    @RequestMapping("/index")
    public String index(String outTradeId,Model model){
        OrderLog orderLog = service.getOrderLog(outTradeId);
        model.addAttribute("total_amount",orderLog.getFee());
        model.addAttribute("out_trade_no",orderLog.getOutTradeId());
        return "index";
    }

    /**
     *
     * @param total_amount  商品金额
     * @param out_trade_no  订单号
     * @param model
     * @return
     */
    @RequestMapping("/succeed")
    public String succeed(String total_amount,String out_trade_no,Model model){
        model.addAttribute("total_amount",total_amount);
        model.addAttribute("out_trade_no",out_trade_no);
        //查单
        return "succeed";
    }
}
