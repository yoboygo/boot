package ml.idream.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AsyncDreamConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
       /* ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("MyDream-");*/
        ExecutorService executor = Executors.newFixedThreadPool(10);
        return executor;
    }
}
