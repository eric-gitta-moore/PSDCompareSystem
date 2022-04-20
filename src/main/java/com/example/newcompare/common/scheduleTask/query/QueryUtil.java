package com.example.newcompare.common.scheduleTask.query;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.newcompare.common.utils.FileDownloadUtil;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.User;
import com.example.newcompare.mapper.OrderLogMapper;
import com.example.newcompare.service.OrderLogService;
import com.example.newcompare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QueryUtil {
    @Autowired
    RestTemplate template;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    UserService userService;

    @Autowired
    OrderLogMapper orderLogMapper;

    /**
     * 查询对比结果
     * @param workCode
     * @return
     * @throws Exception
     * @author 不吃糖
     */
    public final boolean query(String workCode) throws Exception {
        ResponseEntity<String> forEntity = template.getForEntity("http://139.9.203.100:9721/cadpare/status?workcode="+workCode, String.class);
        String body = forEntity.getBody();
        System.out.println(body);
        CompanyResult result = JSON.parseObject(body,CompanyResult.class);
        String s = null;
        if (body.contains("IDENTICAL")){
            s = "无差异";
        }else if (body.contains("Drawing size mismatch")){
            s = "图片尺寸不一致";
        } else if (result.getData().getDoneCount()!=null && result.getData().getDoneCount()==2){
            s = "有差异";
        }else if(result.getErrcode()==401){
            s = "对比出错";
        } else if (result.getData().getTotal()==null){
            s = "正在对比";
        }

        if(s!=null && !s.equals("正在对比")){
            orderLogService.update(new UpdateWrapper<OrderLog>().eq("work_code",workCode).set("result",s).set("status","complete"));
            if(s.equals("有差异") || s.equals("有差异")){
                String[] url = FileDownloadUtil.url(workCode);
                int count = orderLogMapper.getCount(url[3]);
                System.out.println(233);
                if (count>=1){
                    System.out.println(123);
                    String[] split = url[3].split("\\.");
                    split[split.length-2]+=count+"";
                    StringBuilder name=new StringBuilder();
                    for (String s1 : split) {
                        name.append(s1).append(".");
                    }
                    name.deleteCharAt(name.length()-1);
                    orderLogService.update(new UpdateWrapper<OrderLog>().eq("work_code",workCode).set("resolution",url[2]).set("size",url[1]).set("url",url[0]).set("file_name",name.toString()));
                } else {
                    orderLogService.update(new UpdateWrapper<OrderLog>().eq("work_code",workCode).set("resolution",url[2]).set("size",url[1]).set("url",url[0]).set("file_name",url[3]));
                }

            }else {
                //对比不成功退款到余额
                User user = userService.getById("1");
                OrderLog orderLog = orderLogService.getOne(new QueryWrapper<OrderLog>().eq("work_code",workCode));
                user.setBalance(user.getBalance()+Float.parseFloat(orderLog.getFee().toString()));
                userService.update(user,null);
            }
            return true;
        }
        return false;
    }
}
