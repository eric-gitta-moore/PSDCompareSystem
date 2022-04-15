package com.example.newcompare.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.newcompare.common.utils.AlipayUtil;
import com.example.newcompare.common.utils.ChangeToMapUtil;
import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.TaskGroup;
import com.example.newcompare.service.OrderLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
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



    @Resource
    OrderLogService service;






    @DeleteMapping("/delete")
    @ApiOperation(value = "郑前===》批量删除，Ids: string数组的订单Id")
    public Result delete(@RequestParam("Ids") String[] Ids){
        return service.remove(new LambdaQueryWrapper<OrderLog>().in(OrderLog::getId,Ids)) ? Result.success(200,"删除成功","") : Result.fail(400,"删除失败","");
    }



//    @ApiOperation("徐启峰====>修改订单的状态")
//    @PutMapping("/changeState")
//    public Result changeState(String workCode) throws Exception {
//        int responseCode = CompareQueryUtil.querycomparestatus(workCode);
//        OrderLog orderLog = service.getByWorkCode(workCode);
//        if(orderLog==null||orderLog.getDeleted()==1) {
//            return Result.fail(400,"订单不存在,无法修改订单状态！",null);
//        }
//        if(responseCode==400){
//            //修改对比状态为失败
//            orderLog.setStatus("fail");
//            boolean b = service.updateById(orderLog);
//            if (b) {
//                return Result.fail(400,"文件对比失败！成功修改订单状态为失败",null);
//            } else {
//                return Result.fail(400,"文件对比失败！未成功修改订单状态为失败",null);
//            }
//        }
//        //修改对比状态为成功
//        orderLog.setStatus("complete");
//        //向公司服务器发送请求获取文件的大小，分辨率，路径并保存。
//        Integer id = orderLog.getId();
//        String[] url = FileDownloadUtil.url(workCode, id);
//        orderLog.setUrl(url[0]);
//        orderLog.setSize(url[1]);
//        orderLog.setResolution(url[2]);
//        boolean b = service.updateById(orderLog);
//        if (b) {
//            return Result.success(200,"对比成功,url保存成功！",null);
//        } else {
//            return Result.fail(400,"对比成功，但保存url失败！",null);
//        }
//    }










}
