package ml.idream.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Aspect
public class LogAspectConfig {

    @Pointcut("@annotation(ml.idream.log.DreamLog)")
    public void globalLogPointCut(){}

    @Before("globalLogPointCut()")
    public void before(JoinPoint joinPoint){
        MethodSignature target = (MethodSignature)joinPoint.getSignature();
        DreamLog logAnnotation = target.getMethod().getAnnotation(DreamLog.class);
        if(logAnnotation !=null ){
            System.out.println(target.getName() + "设置了日志记录：name=" + logAnnotation.name() + " value = " + logAnnotation.value());
        }
    }

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
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
                .version("2.8.0")
                .build();
    }
}
