package ml.idream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
* Spring boot MainApp
* */
@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = "ml.idream")
public class IDreamApp extends AbstractSecurityWebApplicationInitializer {

    public static void main(String[] args){
        SpringApplication.run(IDreamApp.class);
    }

}
