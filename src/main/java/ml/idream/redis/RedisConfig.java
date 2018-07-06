package ml.idream.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

@Profile("dey")
@Configuration
public class RedisConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter,MessageListener messageListener) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(connectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic("demo"));
        redisMessageListenerContainer.addMessageListener(messageListener, new PatternTopic("demo"));
        return redisMessageListenerContainer;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    public MessageListener messageListener(){
        return  new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                System.out.println("Listener : " + new String(message.getBody()));
            }
        };
    }

    @Bean
    public Receiver receiver(CountDownLatch latch) {
        return new Receiver(latch);
    }

    @Bean
    public CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}
