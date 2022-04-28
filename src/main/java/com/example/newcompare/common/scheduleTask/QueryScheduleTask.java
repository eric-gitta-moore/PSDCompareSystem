package com.example.newcompare.common.scheduleTask;

import com.example.newcompare.common.scheduleTask.query.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

/**
 * @author 不吃糖
 */
@Configuration
public class QueryScheduleTask {

    @Autowired
    QueryUtil queryUtil;
    @Autowired
    StringRedisTemplate template;

    /**
     * 定时查询未对比完的对比信息
     */
    @Scheduled(cron = "0/8 * * * * ?")
    private void doTask() throws Exception {

        Set<String> orders = template.opsForSet().members("order");
        for (String order : orders) {
            synchronized (order){
                if (template.opsForSet().isMember("order", order)) {
                    boolean query = queryUtil.query(order);
                    if (query) {
                        template.opsForSet().remove("order", order);
                    }
                }
            }
        }
    }
}
