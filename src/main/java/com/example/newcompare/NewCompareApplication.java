package com.example.newcompare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.example.newcompare.mapper")
public class NewCompareApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewCompareApplication.class, args);
    }

}
