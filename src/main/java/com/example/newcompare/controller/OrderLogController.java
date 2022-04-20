package com.example.newcompare.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.newcompare.common.utils.AlipayUtil;
import com.example.newcompare.common.utils.ChangeToMapUtil;
import com.example.newcompare.common.utils.FileDownloadUtil;
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

    @Autowired
    AlipayUtil alipayUtil;

    @DeleteMapping("/delete")
    @ApiOperation(value = "郑前===》批量删除，Ids: string数组的订单Id")
    public Result delete(@RequestParam("Ids") String[] Ids) {
        int i = service.orderDelete(Ids);
        //根据i的值避免重复操作返回错误信息
        if (i > 0) {
            return Result.success("成功");
        } else {
            return Result.fail("失败");
        }
    }

}
