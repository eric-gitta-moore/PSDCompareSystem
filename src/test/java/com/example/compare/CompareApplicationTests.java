package com.example.compare;

import com.example.compare.mapper.CompareMapper;
import com.example.compare.mapper.OrderLogMapper;
import com.example.compare.service.OrderLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class CompareApplicationTests {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    OrderLogService service;

    @Autowired
    OrderLogMapper mapper;
    @Test
    void contextLoads() {
//        redisTemplate.opsForValue().set("123","123",100, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("qdawfawf"));
    }

    @Test
    void ttt(){
//        service.insertOrderLog();
        System.out.println(mapper.getCompareIdByOrderId("9f829815-1532-4db6-9116-3961ed6550c2"));
    }

}
