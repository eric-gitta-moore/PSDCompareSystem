package com.example.compare.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.example.compare.entity.OrderLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class AlipayUtil {

    @Value("${alipay.appid}")
    private String appid;
    @Value("${alipay.url}")
    private String url;
    @Value("${alipay.privateKey}")
    private String privateKey;
    @Value("${alipay.publicKey}")
    private String publicKey;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    /**
     *
     *  AlipayClient alipayClient = new DefaultAlipayClient(url,appid,privateKey,"json","UTf-8",publicKey,"RSA2");
     */


    public String pay(OrderLog orderLog){

        AlipayClient alipayClient = new DefaultAlipayClient(url,appid,privateKey,"json","UTf-8",publicKey,"RSA2");


        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);

        //使用这个数据结构对象,可以使用put方法给json对象添加元素
        JSONObject bizContent = new JSONObject();


        //	商户网站唯一订单号
        bizContent.put("out_trade_no", orderLog.getOutTradeId());
        //	订单总金额。
        bizContent.put("total_amount", orderLog.getFee());
        //订单标题
        bizContent.put("subject", orderLog.getTitle());
        //销售产品码
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        //设置绝对超时时间:15分钟
        bizContent.put("time_expire", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime() + 15*60*1000));

        request.setBizContent(bizContent.toString());

        AlipayTradeWapPayResponse response = null;
        String form = null;

        try {
            response = alipayClient.pageExecute(request);
            form = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return form;
    }



}
