package com.example.newcompare.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.newcompare.common.utils.QRCodeUtil;
import com.example.newcompare.common.utils.Result;
import com.example.newcompare.entity.File;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.ResponseResult;
import com.example.newcompare.entity.WorkCode;
import com.example.newcompare.service.OrderLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@Api(value = "order-log")
@Controller
@RequestMapping("/order-log")
@RestController
@ResponseBody
public class OrderLogController {
    @Resource
    OrderLogService service;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;
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

    @DeleteMapping("/delete")
    @ApiOperation(value = "郑前===》批量删除，Ids: string数组的订单Id")
    public Result delete(@RequestParam("Ids") String[] Ids){
        service.allDelete(Ids);
        return Result.success("成功");
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

    @ApiOperation("李超===>启动对比任务，参数：order_log对应的id")
    @PostMapping("/stertCompare")
    public Result stertCompare(Integer id) throws IOException {
        //通过order-log的id查到在order_log表中对应的数据
        OrderLog orderLog = service.selectById(id);
        //判断状态，不是创建订单状态则不进行对比
        if(orderLog.getStatus().equals("create"))
        {
            //根据上面查出来的order_log的对象拿到其对应的任务id(tsak_id)
            Integer taskId = orderLog.getTaskId();
            //拿到taskId后去task_group表中查到对应对比任务的工作码
            String workCode = service.getWorkCodeByTaskId(taskId);
            //再根据taskId查到file表中对应对比前的两张图片的信息
            List<File> files = service.getFilecodeByTaskId(taskId);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            Map<String,Object> request=new HashMap<>();
            request.put("workcode", workCode);
            request.put("pair", files.get(0)+":"+files.get(1));
            HttpEntity<Map<String,Object>> httpEntity=new HttpEntity<>(request,httpHeaders);
            ResponseResult responseResult =
                    restTemplate.postForObject("http://139.9.203.100:9721/cadpare/start", httpEntity, ResponseResult.class);
            Integer errcode = responseResult.getErrcode();
            if(errcode != 0)
            {
                return Result.success(400,"启动对比失败！",null);
            }
            return Result.success(200,"启动对比成功","http://139.9.203.100:9721/cadpare/status?workcode="+workCode);
        }
        else
        {
            return Result.fail(400,"启动对比失败，原因：文件上传失败，无法对比",null);
        }
    }
}
