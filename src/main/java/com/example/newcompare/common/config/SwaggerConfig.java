package com.example.newcompare.common.config;


import com.example.newcompare.common.properties.BaseProperties;
import com.example.newcompare.common.properties.SwaggerProperties;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *
 * @author
 * @date 2022/3/19
 */

@Configuration
@EnableOpenApi
@EnableKnife4j
public class SwaggerConfig {

    @Autowired
    private BaseProperties properties;

    @Bean
    public Docket createRestApi(Environment environment) {

        SwaggerProperties swagger = properties.getSwagger();
        Profiles profile=Profiles.of("dev","test");
        boolean flag = environment.acceptsProfiles(profile);
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo(swagger))
                .enable(flag)
                .groupName("软工20")
                .select()
                .apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
                .build()
                .protocols(newHashSet("http", "https"))
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo(SwaggerProperties swagger) {
        return new ApiInfoBuilder()
                .title(swagger.getTitle())
                .description(swagger.getDescription())
                .version(swagger.getVersion())
                .build();
    }


    /**
     * 支持的通讯协议集合
     *
     * @param type1
     * @param type2
     * @return
     */
    private Set<String> newHashSet(String type1, String type2) {
        Set<String> set = new HashSet<>();
        set.add(type1);
        set.add(type2);
        return set;
    }

    /**
     * 认证的安全上下文
     */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("token", "token", "header"));
        return securitySchemes;
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }




}
