package com.example.newcompare.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.service.OrderLogService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@Data
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
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    OrderLogService service;

    /**
     * 查单
     *
     * @param outTradeNo 商户订单号
     * @return AlipayTradeQueryResponse
     */
    public AlipayTradeQueryResponse queryTrade(String outTradeNo) {
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(url, appid, privateKey, "json", "UTf-8", publicKey, "RSA2");
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", outTradeNo);
            //bizContent.put("trade_no", "2014112611001004680073956707");
            request.setBizContent(bizContent.toString());
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                System.out.println(response.getBody());
                return response;
            } else {
                return null;
            }
        } catch (AlipayApiException e) {
            System.out.println(e.getErrMsg());
        }
        return null;
    }

    /**
     * 查单
     * @param outTradeNo 商户订单号
     * @return 交易状态 WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
     */
    public String queryTradeStatus(String outTradeNo) {
        AlipayTradeQueryResponse response = this.queryTrade(outTradeNo);
        if (response != null) {
            System.out.println(response.getTradeStatus());
            return response.getTradeStatus();
        } else {
            return null;
        }
    }

    public String pay(OrderLog orderLog) {

        AlipayClient alipayClient = new DefaultAlipayClient(url, appid, privateKey, "json", "UTf-8", publicKey, "RSA2");


        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);

        //使用这个数据结构对象,可以使用put方法给json对象添加元素
        JSONObject bizContent = new JSONObject();


        //	商户网站唯一订单号
//        bizContent.put("out_trade_no", orderLog.getOutTradeId());
        //	订单总金额。
        bizContent.put("total_amount", orderLog.getFee());
        //订单标题
        bizContent.put("subject", orderLog.getTitle());
        //销售产品码
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        //设置绝对超时时间:15分钟
        bizContent.put("timeout_express", "15m");
//        bizContent.put("time_expire", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime() + 15*60*1000));

        request.setBizContent(bizContent.toString());

        AlipayTradeWapPayResponse response = null;
        String form = null;

        try {
            response = alipayClient.pageExecute(request);
            form = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            //redis，nosugar
//            redisTemplate.opsForValue().set(service.getCompareIdByOrderId(orderLog.getOutTradeId().toString()), "未支付", 15, TimeUnit.SECONDS);
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return form;
    }


}
