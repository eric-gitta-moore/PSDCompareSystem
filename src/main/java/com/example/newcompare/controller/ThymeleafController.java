package com.example.newcompare.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.newcompare.common.utils.AlipayUtil;
import com.example.newcompare.common.utils.ChangeToMapUtil;
import com.example.newcompare.entity.File;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.Recharge;
import com.example.newcompare.service.OrderLogService;
import com.example.newcompare.service.RechargeService;
import com.example.newcompare.service.impl.OrderLogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/thymeleaf")
@Api(value = "ThymeleafController",tags = "thymeleaf")
public class ThymeleafController {

    @Autowired
    AlipayUtil alipayUtil;

    @Autowired
    OrderLogServiceImpl orderLogService;

    @Autowired
    RechargeService rechargeService;

    /**
     * 跳转到收银台
     * @param id  订单编号
     * @param model
     * @return
     */
    @ApiOperation(value = "跳转收银台界面， outTradeId: 订单编号")
    @RequestMapping("/index")
    public String index(Integer id,Model model){
        Recharge recharge = rechargeService.getRechargeById(id);
        model.addAttribute("total_amount",recharge.getFee());
        model.addAttribute("out_trade_no", recharge.getOutTradeNo());
        model.addAttribute("id", id);
        return "index";
    }

    /**
     *
     * @param model
     * @param request
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("/payFinished")
    public String succeed(Model model, HttpServletRequest request) throws AlipayApiException {
        String out_trade_no = request.getParameter("out_trade_no");
        String total_amount = request.getParameter("total_amount");
        String msg = null;
        //进行同步验签
        Map<String, String> paramsMap = ChangeToMapUtil.convertRequestParamsToMap(request);
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, alipayUtil.getPublicKey(), paramsMap.get("charset"), paramsMap.get("sign_type")); //调用SDK验证签名
        if (signVerified) {//验签成功

            if (!rechargeService.checkOrderAndUpdateDatabase(out_trade_no)) {
                msg = "支付失败";
            } else {
                msg = "支付成功";
            }
        } else {
            msg = "支付失败";
            //这里给出错误页面。如果验签失败绝对是非法入侵
        }
        model.addAttribute("total_amount", total_amount);
        model.addAttribute("out_trade_no", out_trade_no);
        model.addAttribute("msg", msg);
        return "succeed";
    }

}
