package ml.idream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
//                .produces(Sets.newHashSet("application/json"))
//                .consumes(Sets.newHashSet("application/json"))
//                .protocols(Sets.newHashSet("http","https"))
//                .forCodeGeneration(true)
                .groupName("private-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ml.idream"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("SpringBoot利用swagger构建API文档")
                .description("简单优雅的restfun风格")
                .version("1.0.0")
                .build();
    }
}
