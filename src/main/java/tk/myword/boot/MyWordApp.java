package tk.myword.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "tk.myword.boot")
public class MyWordApp {

    public static void main(String[] args) {
        SpringApplication.run(MyWordApp.class,args);
    }
}
