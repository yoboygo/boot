package ml.idream;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
* Spring boot MainApp
* */
@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "ml.idream")
@MapperScan(basePackages = "ml.idream")
public class IDreamApp extends AbstractSecurityWebApplicationInitializer {

    public static void main(String[] args){
        SpringApplication.run(IDreamApp.class);
    }

}
