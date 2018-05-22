package com.idengyun.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = "com.idengyun")
public class DengyunMainApp implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(DengyunMainApp.class,args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/add/activity").setViewName("/idengyun/activity/main");
    }

}

