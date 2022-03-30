package com.example.compare.controller;



import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.compare.common.utils.AlipayUtil;
import com.example.compare.common.utils.ChangeToMapUtil;
import com.example.compare.common.utils.QRCodeUtil;
import com.example.compare.common.utils.Result;
import com.example.compare.entity.OrderLog;
import com.example.compare.service.CompareService;
import com.example.compare.service.OrderLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 支付订单表 前端控制器
 * </p>
 *
 * @author nosgua
 * @since 2022-03-26
 */
@Controller
@RequestMapping("/order-log")
@Api(value = "OrderLogController")
public class OrderLogController {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
   AlipayUtil alipayUtil;
    @Autowired
    OrderLogService service;

    @Autowired
    CompareService service2;
    /**
     * 获取跳转支付界面的二维码
     * @param url 跳转的url
     * @param size 二维码大小
     * @param response
     * @throws IOException
     */
    @ApiOperation("刘锦堂===>获取跳转支付界面的二维码，url： 跳转的url，size： 二维码大小，默认值250")
    @GetMapping("/getQRCode")
    public void getQRCode(String url, Integer size, HttpServletResponse response) throws IOException {
        BufferedImage qr = QRCodeUtil.getBufferedImage(url, size);
        ImageIO.write(qr,"jpg",response.getOutputStream());
//        return Result.success()
    }


    @ApiOperation("朱涵===>发起请求支付")
    @GetMapping("/topay/{id}")
    public String topay(@PathVariable String id, Model model) {
        OrderLog orderLog = service.getOrderLog(id);
        String form = service.AlipayUtils(orderLog);
        model.addAttribute("form",form);
        //填入redis
        redisTemplate.opsForValue().set(id,"未支付",15, TimeUnit.MINUTES);
        return "pay";
    }



    @ApiOperation("查询订单交易状态，WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）")
    @GetMapping("/checkOrderStatus")
    public void checkOrderStatus(String outTradeNo,HttpServletResponse response) throws IOException {
        boolean status = service.checkOrderAndUpdateDatabase(outTradeNo);
        response.getWriter().print(status?"success":"fail");
    }

    /**
     * 获取支付宝返回的订单信息
     * @param request
     * @param response
     *
     */

    @ApiOperation("肖恒宇===>获取支付宝返回的订单信息")
    @PostMapping("/notify")
    public void  getAiLiPay(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {

        Map<String,String> paramsMap= ChangeToMapUtil.convertRequestParamsToMap(request);
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, alipayUtil.getPublicKey() ,paramsMap.get("charset"), paramsMap.get("sign_type")); //调用SDK验证签名
        if(signVerified){
            // 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            OrderLog out_trade_no = service.getDiffInformation(paramsMap.get("out_trade_no"));

            if(alipayUtil.getAppid().equals(paramsMap.get("app_id"))){

            }
            else if(paramsMap.get("out_trade_no").equals(out_trade_no.getOutTradeId())){

            }
            else if(paramsMap.get("total_amount").equals( out_trade_no.getFee().toString())){

            }else{
                response.getWriter().print("fail");
                return;
            }


            if(paramsMap.get("trade_status").equals("TRADE_SUCCESS") || paramsMap.get("trade_status").equals("TRADE_FINISHED")){


                if( service.updateOrderStatus(out_trade_no) && service2.updateCompareStatus(out_trade_no.getId())){
                    //修改redis中的数据
                    redisTemplate.opsForValue().set(service.getCompareIdByOrderId(out_trade_no.getOutTradeId()),"已支付",10,TimeUnit.MINUTES);
                    response.getWriter().print("success");
                }else {
                    response.getWriter().print("fail");
                }

            }

        }else{
            // 验签失败则记录异常日志，并在response中返回failure.
            response.getWriter().print("fail");
        }
    }
}
