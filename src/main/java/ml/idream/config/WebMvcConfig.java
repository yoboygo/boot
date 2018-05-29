package ml.idream.config;

import ml.idream.global.CounterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public CounterInterceptor myInterceptor() {
        return new CounterInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor());
    }

    /*
     * smart controller
     * */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/home");
        registry.addViewController("/home").setViewName("/home");
        registry.addViewController("/login").setViewName("/user/login");
        registry.addViewController("/logout").setViewName("/user/logout");
        registry.addViewController("/error").setViewName("/error");
        registry.addViewController("/manager/toUpload").setViewName("/manager/upload");
        registry.addViewController("/dy/activity/add").setViewName("/idengyun/activity/main");

        registry.addViewController("/index").setViewName("/index");
    }

    /*
     * 使之接受xx.yy类型的值
     * */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    /*
     *Spring boot 自己带了 MultipartFile
     * */
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        return multipartResolver;
    }

}
