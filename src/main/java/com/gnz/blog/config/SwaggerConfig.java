package com.gnz.blog.config;

import com.github.xiaoymin.knife4j.spring.annotations.*;
import com.gnz.blog.constants.JwtConstants;
import org.springframework.context.annotation.*;
import org.springframework.core.env.*;
import springfox.bean.validators.configuration.*;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger2.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket docket(Environment environment) {
        // 如果在dev环境（开发环境）就开启Swagger
        boolean isDev = environment.acceptsProfiles(Profiles.of("dev"));
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        // 调试中可以修改请求头
        ticketPar.name(JwtConstants.TOKEN_HEADER)
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("默认接口")
                .enable(isDev)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gnz.blog.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }


    /**
     * 配置Swagger的ApiInfo
     *
     * @return API配置信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("让你随风而逝的后端接口文档")
                .description("通过此文档可以查看、测试后端api")
                .contact(new Contact("耿宁泽", "http://letyougowiththewind.cn/", "gengningze@163.com"))
                .version("v1.0.0")
                .build();
    }
}
