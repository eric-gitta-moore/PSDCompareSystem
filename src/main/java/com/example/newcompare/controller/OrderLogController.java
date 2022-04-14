package com.example.newcompare.controller;

import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.newcompare.common.utils.CompareQueryUtil;
import com.example.newcompare.common.utils.FileDownloadUtil;
import com.example.newcompare.common.utils.AlipayUtil;
import com.example.newcompare.common.utils.ChangeToMapUtil;
import com.example.newcompare.common.utils.QRCodeUtil;
import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.service.OrderLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 支付订单表 前端控制器
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@Api(tags = "Order-log")
@Controller
@RequestMapping("/order-log")
@ResponseBody
public class OrderLogController {
    @Autowired
    private OrderLogService service;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    @Qualifier("alipayUtil")
    private AlipayUtil alipayUtil;

    /**
     * 获取跳转支付界面的二维码
     *
     * @param size     二维码大小
     * @param response
     * @throws IOException
     */
    @ApiOperation("刘锦堂===>获取跳转支付界面的二维码，id：compare表id，size： 二维码大小，默认值250")
    @GetMapping("/getQRCode")
    public void getQRCode(Integer id, Integer size, HttpServletResponse response) throws IOException {
        String url = "http://114.55.0.204:8081/thymeleaf/index?id=" + id;
        BufferedImage qr = QRCodeUtil.getBufferedImage(url, size);
        ImageIO.write(qr, "jpg", response.getOutputStream());
//        return Result.success()
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "郑前===》批量删除，Ids: string数组的订单Id")
    public Result delete(@RequestParam("Ids") String[] Ids) {
        int i = service.orderDelete(Ids);
        //根据i的值避免重复操作返回错误信息
        if (i > 0)
            return Result.success("成功");
        else
            return Result.fail("失败");
    }

    @ApiOperation("朱涵===>发起请求支付")
    @GetMapping("/topay/{id}")
    public String topay(@PathVariable String id, Model model) {
        OrderLog orderLog = service.getOrderLog(id);
        String form = service.useAlipayUtils(orderLog);
        model.addAttribute("form", form);
        //填入redis
        redisTemplate.opsForValue().set(id, "未支付", 15, TimeUnit.MINUTES);
        return "pay";
    }

    @ApiOperation("徐启峰====>修改订单的状态")
    @PutMapping("/changeState")
    public Result changeState(String workCode) throws Exception {
        int responseCode = CompareQueryUtil.querycomparestatus(workCode);
        OrderLog orderLog = service.getByWorkCode(workCode);
        if (orderLog == null || orderLog.getDeleted() == 1)
            return Result.fail(400, "订单不存在,无法修改订单状态！", null);
        if (responseCode == 400) {
            //修改对比状态为失败
            orderLog.setStatus("fail");
            boolean b = service.updateById(orderLog);
            if (b)
                return Result.fail(400, "文件对比失败！成功修改订单状态为失败", null);
            else
                return Result.fail(400, "文件对比失败！未成功修改订单状态为失败", null);
        }
        //修改对比状态为成功
        orderLog.setStatus("complete");
        //向公司服务器发送请求获取文件的大小，分辨率，路径并保存。
        Integer id = orderLog.getId();
        String[] url = FileDownloadUtil.url(workCode, id);
        orderLog.setUrl(url[0]);
        orderLog.setSize(url[1]);
        orderLog.setResolution(url[2]);
        boolean b = service.updateById(orderLog);
        if (b)
            return Result.success(200, "对比成功,url保存成功！", null);
        else
            return Result.fail(400, "对比成功，但保存url失败！", null);
    }

    /**
     * 获取支付宝返回的订单信息
     *
     * @param request
     * @param response
     */

    @ApiOperation("肖恒宇===>获取支付宝返回的订单信息")
    @PostMapping("/notify")
    public void getAiLiPay(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {

        Map<String, String> paramsMap = ChangeToMapUtil.convertRequestParamsToMap(request);
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, alipayUtil.getPublicKey(), paramsMap.get("charset"), paramsMap.get("sign_type")); //调用SDK验证签名
        if (signVerified) {
            // 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            OrderLog out_trade_no = service.getDiffInformation(paramsMap.get("out_trade_no"));

            if (alipayUtil.getAppid().equals(paramsMap.get("app_id"))) {

            } else if (paramsMap.get("out_trade_no").equals(out_trade_no.getOutTradeId())) {

            } else if (paramsMap.get("total_amount").equals(out_trade_no.getFee().toString())) {

            } else {
                response.getWriter().print("fail");
                return;
            }

            if (paramsMap.get("trade_status").equals("TRADE_SUCCESS") || paramsMap.get("trade_status").equals("TRADE_FINISHED")) {


                if (service.updateStatus(out_trade_no)) {
                    response.getWriter().print("success");
                } else {
                    response.getWriter().print("fail");
                }

            }

        } else {
            // 验签失败则记录异常日志，并在response中返回failure.
            response.getWriter().print("fail");
        }
    }


}
