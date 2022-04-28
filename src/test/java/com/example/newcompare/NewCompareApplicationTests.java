package com.example.newcompare;


import com.example.newcompare.service.OrderLogService;
import com.qiniu.util.Json;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@SpringBootTest
class NewCompareApplicationTests {
//
//    @Autowired
//    CompareMapper mapper;
//    @Test
//    void contextLoads() {
////        final List<Compare> compares = mapper.selectList(null);
////        for (Compare compare : compares) {
////            System.out.println(compare);
////        }
//        mapper.delete(new UpdateWrapper<Compare>().eq("id","209"));
//
//    }
//    @Autowired
//    RestTemplate template;
//    @Test
//    void test(){
//        ResponseEntity<String> forEntity = template.getForEntity("http://139.9.203.100:9721/cadpare/status?workcode=awfawfawfw", String.class);
//        String body = forEntity.getBody();
//        System.out.println(body);
//        CompanyResult result = JSON.parseObject(body,CompanyResult.class);
//        System.out.println(result);
////        System.out.println(decode);
//        if (body.contains("IDENTICAL")){
//            System.out.println("无差异");
//        }else if (body.contains("Drawing size mismatch")){
//            System.out.println("图片尺寸不一致");
//        } else if (result.getData().getDoneCount()!=null && result.getData().getDoneCount()==2){
//            System.out.println("有差异");
//        }else if(result.getErrcode()==401){
//            System.out.println("对比出错");
//        } else if (result.getData().getTotal()==null){
//            System.out.println("正在对比");
//        }
//    }
//
//    @Autowired
//    StringRedisTemplate redisTemplate;
//
//    @Test
//    void test2(){
//        Set<String> members = redisTemplate.opsForSet().members("k1");
//        for (String member : members) {
//            System.out.println(member);
//        }
//        redisTemplate.opsForSet().remove("k1","123");
//    }

    @Autowired
    OrderLogService orderLogService;
    @Test
    void test3(){

//        final OrderLog work_code = orderLogService.getOne(new QueryWrapper<OrderLog>().eq("work_code", "123124124124"));
//
//        System.out.println(work_code);

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        System.out.println(formatter.format(new Date()));



    }

}
