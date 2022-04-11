package com.example.newcompare.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.newcompare.common.utils.QRCodeUtil;
import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.service.OrderLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支付订单表 前端控制器
 * </p>
 *
 * @author nosgua
 * @since 2022-04-10
 */
@Api(value = "order-log")
@Controller
@RequestMapping("/order-log")
public class OrderLogController {
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
    public void getQRCode(Integer id, Integer size, HttpServletResponse response) throws IOException {
        String url = "http://114.55.0.204:8081/thymeleaf/index?id="+id;
        BufferedImage qr = QRCodeUtil.getBufferedImage(url, size);
        ImageIO.write(qr,"jpg",response.getOutputStream());
//        return Result.success()
    }

    @PostMapping("/search")
    @ApiOperation(value = "历史记录分页查询,keywords: 流水号或者支付状态" +
            "（支付状态: unpaid 未完成，complete 已经完成）," +
            "maxPage: 每页显示最大数量，" +
            "startPage: 开始页码,startTime和endTime: 要查询的时间段")
    public Result search(@RequestBody Map<String, String> map) {
        //最大显示数量默认是10
        int maxPage = 10;
        //起始页码默认为是1
        int startPage = 1;
        String mPage = map.get("maxPage");
        String sPage = map.get("startPage");
        String keyWords = map.get("keyWords");
        String startTime = map.get("startTime");
        String endTime = map.get("endTime");
        if (sPage != null) {
            startPage = Integer.parseInt(sPage);
        }
        if (mPage != null) {
            maxPage = Integer.parseInt(mPage);
        }
        Page<OrderLog> Page = new Page(startPage,maxPage);
        return Result.success(service.getHistory(Page, keyWords, startTime, endTime));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "批量删除，serialNumbers: string数组的流水号")
    public Result delete(@RequestParam("serialNumbers") String[] serialNumbers){
        service.allDelete(serialNumbers);
        return Result.success("成功");
    }
}
