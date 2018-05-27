package ml.idream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/*
* Spring boot MainApp
* */
@SpringBootApplication
@ComponentScan(basePackages = "ml.idream")
public class IDreamApp extends AbstractSecurityWebApplicationInitializer {

    public static void main(String[] args){

        SpringApplication.run(IDreamApp.class);
    }

}
