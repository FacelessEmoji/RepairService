package rut.miit.repairservice.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.TopicExchange;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

    public static final String QUEUE_NAME = "spring-boot"; // Имя очереди
    public static final String EXCHANGE_NAME = "spring-boot-exchange"; // Имя обмена

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false); // Очередь
    }

    @Bean
    public Exchange exchange() {
        return new TopicExchange(EXCHANGE_NAME); // Изменение на TopicExchange
    }
}
