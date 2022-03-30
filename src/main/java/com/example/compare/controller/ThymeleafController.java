package com.example.compare.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.compare.common.utils.AlipayUtil;
import com.example.compare.common.utils.ChangeToMapUtil;
import com.example.compare.entity.OrderLog;
import com.example.compare.service.OrderLogService;
import com.example.compare.service.impl.OrderLogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Api(value = "ThymeleafController",tags = "thymeleaf")
public class ThymeleafController {
    @Autowired
    OrderLogService service;

    @Autowired
    AlipayUtil alipayUtil;

    @Autowired
    OrderLogServiceImpl orderLogService;

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
     * @param model
     * @param request
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping("/succeed")
    public String succeed(Model model, HttpServletRequest request) throws AlipayApiException {

        String out_trade_no = request.getParameter("out_trade_no");
        String total_amount = request.getParameter("total_amount");
        String msg = null;
        //进行同步验签
        Map<String, String> paramsMap = ChangeToMapUtil.convertRequestParamsToMap(request);
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, alipayUtil.getPublicKey(), paramsMap.get("charset"), paramsMap.get("sign_type")); //调用SDK验证签名
        if (signVerified) {//验签成功

            if (!service.checkOrderAndUpdateDatabase(out_trade_no)) {
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
