package tk.codecube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.codecube.config.JpaConfig;

/*
* Spring boot tk.codecube.MainApp
* */
@SpringBootApplication
@EnableAutoConfiguration(exclude = JpaConfig.class)
public class MainApp {
    public static void main(String[] args){

        SpringApplication.run(MainApp.class);
    }

}
