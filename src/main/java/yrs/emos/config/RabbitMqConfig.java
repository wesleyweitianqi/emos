package yrs.emos.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public ConnectionFactory getFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        return factory;
    }
}
