package rut.miit.repairservice.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

    public static final String EXCHANGE_NAME = "spring-boot-exchange"; // Имя обмена

    // Только Exchange, так как очередь создаётся на стороне ConsumerService
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME); // Использование TopicExchange для маршрутизации сообщений
    }
}
