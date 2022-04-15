package com.example.newcompare.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.mapper.OrderLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * 启动时将数据库中未完成的对比加入redis中，
 * @author 不吃糖
 */
@Component
public class MyListener implements ServletContextListener {
    @Autowired
    StringRedisTemplate template;

    @Autowired
    OrderLogMapper mapper;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        List<OrderLog> orderLogs = mapper.selectList(new QueryWrapper<OrderLog>().eq("status", "incomplete"));
        for (OrderLog orderLog : orderLogs) {
            template.opsForSet().add("order",orderLog.getWorkCode());
        }
        System.out.println("加载未完成对比任务到redis中");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
