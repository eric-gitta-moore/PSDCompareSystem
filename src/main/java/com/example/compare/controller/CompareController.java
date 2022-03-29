package com.example.compare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.compare.common.utils.FileDownloadUtil;
import com.example.compare.common.utils.QRCodeUtil;
import com.example.compare.common.utils.Result;

import com.example.compare.entity.Compare;
import com.example.compare.entity.OrderLog;
import com.example.compare.service.CompareService;
import com.example.compare.service.OrderLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/compare")
@Api(value = "CompareController")
public class CompareController {
    @Resource
    private CompareService service;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private OrderLogService orderLogService;

    @Resource
    private CompareService compareService;

    @ApiOperation("李超====>工作码获取接口，通过该接口可以获取工作码用于调用图片上传对比接口，此接口不需要任何参数")
    @GetMapping("/getWorkCode")
    public Result getWorkCode()
    {
        try {
            BigDecimal b = new BigDecimal("100");
            OrderLog orderLog = new OrderLog("unpaid",b, UUID.randomUUID().toString());
            Integer orderId = orderLogService.saveOrderLog(orderLog);

            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateString = formatter.format(currentTime);
            //生成两位随机数拼接在流水号后面
            Random random = new Random();
            int ends = random.nextInt(99);
            String.format("%02d",ends);
            String serialNumber = dateString + ends;
            UUID workCode = UUID.randomUUID();
            Compare compare = new Compare(workCode.toString(),orderId, LocalDateTime.now(),"未支付",serialNumber);
            Integer compareId = compareService.saveCompare(compare);
            return Result.success(200,"获取成功",compare);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(400,"获取失败，请重试！",null);
        }
    }
    /**
     * 获取跳转支付界面的二维码
     * @param id 对比记录id
     * @param size 二维码大小
     * @param response
     * @throws IOException
     */
    @ApiOperation("刘锦堂===>获取跳转支付界面的二维码，id： 对比记录id，size： 二维码大小，默认值250")
    @GetMapping("/getQRCode")
    public void getQRCode(Integer id, Integer size, HttpServletResponse response) throws IOException {
        String outTradeId = service.getOrderIdById(id);
        String url = "http://localhost:8081/"+"index?outTradeId="+outTradeId;
        BufferedImage qr = QRCodeUtil.getBufferedImage(url, size);
        ImageIO.write(qr,"jpg",response.getOutputStream());
//        return Result.success()
    }

    /**
     * 轮训交易状态接口
     * @param id compare记录id
     * @return
     */
    @GetMapping("/getStatus")
    @ApiOperation("刘锦堂===>轮训支付状态，id：compare记录表id")
    public Result getStatus(Integer id){
        String status =redisTemplate.opsForValue().get(id);
        return Result.success(status);
    }

    /**
     * 下载zip文件
     * @param workcode
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
    @ApiOperation("刘锦堂===>下载压缩包文件")
    public void download(String workcode,HttpServletResponse response) throws IOException {
        FileDownloadUtil.downloadZip(workcode,response);
    }
}