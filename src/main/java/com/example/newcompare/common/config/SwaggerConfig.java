package com.example.newcompare.common.config;


import com.example.newcompare.common.properties.BaseProperties;
import com.example.newcompare.common.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author
 * @date 2022/3/19
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private BaseProperties properties;

    @Bean
    public Docket createRestApi(Environment environment) {

        SwaggerProperties swagger = properties.getSwagger();
        Profiles profile=Profiles.of("dev","test");
        boolean flag = environment.acceptsProfiles(profile);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(swagger))
                .enable(flag)
                .groupName("软工20")
                .select()
                .apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
                .build();
    }

    private ApiInfo apiInfo(SwaggerProperties swagger) {
        return new ApiInfoBuilder()
                .title(swagger.getTitle())
                .description(swagger.getDescription())
                .termsOfServiceUrl(swagger.getUrl())
                .contact(new Contact(swagger.getAuthor(), swagger.getUrl(), swagger.getEmail()))
                .version(swagger.getVersion())
                .build();
    }



}
