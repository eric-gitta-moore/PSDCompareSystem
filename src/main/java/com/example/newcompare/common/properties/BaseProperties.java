package com.example.newcompare.common.properties;


import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * 系统配置类定义
 *
 * @author
 * @date 2021/3/18
 */

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:base.properties"},encoding = "utf-8")
@ConfigurationProperties(prefix = "base")
public class BaseProperties {

    private SwaggerProperties swagger = new SwaggerProperties();

}
