package ml.idream.config;

import ml.idream.global.CounterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

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
        registry.addViewController("/login").setViewName("/login");
//        registry.addViewController("/signup").setViewName("/signup");
        registry.addViewController("/error").setViewName("/error");
        registry.addViewController("/manage/toUpload").setViewName("/manager/upload");
        registry.addViewController("/manage").setViewName("/manager/manager");
        registry.addViewController("/dy/activity/add").setViewName("/idengyun/activity/main");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
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
