package com.example.newcompare.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.newcompare.common.utils.AlipayUtil;
import com.example.newcompare.common.utils.ChangeToMapUtil;
import com.example.newcompare.common.utils.QRCodeUtil;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.Recharge;
import com.example.newcompare.exception.QRException;
import com.example.newcompare.service.OrderLogService;
import com.example.newcompare.service.RechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api("recharge")
@RestController
@RequestMapping("/recharge")
public class RechargeController {
    @Autowired
    @Qualifier("alipayUtil")
    AlipayUtil alipayUtil;
    @Autowired
    RechargeService rechargeService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Resource
    OrderLogService service;
    /**
     * 获取跳转支付界面的二维码
     * @param size 二维码大小
     * @param response
     * @throws IOException
     */
    @ApiOperation("刘锦堂===>获取跳转支付界面的二维码，id：compare表id，size： 二维码大小，默认值250")
    @GetMapping("/getQRCode")
    public void getQRCode(Integer size,String fee, HttpServletResponse response) throws IOException, QRException {
        Recharge recharge = new Recharge();
        recharge.setFee(fee);
        recharge.setTitle("充值");
        recharge.setOutTradeNo(UUID.randomUUID().toString());
        recharge.setStatus("unpaid");
        int insert = rechargeService.insert(recharge);
        if (insert==0){
            throw new QRException();
        }
        String url = "http://114.55.0.204:8081/thymeleaf/index?id="+recharge.getId();
        BufferedImage qr = QRCodeUtil.getBufferedImage(url, size);
        ImageIO.write(qr,"jpg",response.getOutputStream());

//        return Result.success()
    }

    @ApiOperation("朱涵===>发起请求支付")
    @GetMapping("/topay/{id}")
    public String topay(@PathVariable String id, Model model) {
        OrderLog orderLog = service.getOrderLog(id);
        String form = service.useAlipayUtils(orderLog);
        model.addAttribute("form",form);
        //填入redis
        redisTemplate.opsForValue().set(id,"未支付",15, TimeUnit.MINUTES);
        return "pay";
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
//            else if(paramsMap.get("out_trade_no").equals(out_trade_no.getOutTradeId())){
//
//            }
            else if(paramsMap.get("total_amount").equals( out_trade_no.getFee().toString())){

            }else{
                response.getWriter().print("fail");
                return;
            }

            if(paramsMap.get("trade_status").equals("TRADE_SUCCESS") || paramsMap.get("trade_status").equals("TRADE_FINISHED")){


                if( service.updateStatus(out_trade_no)){
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
