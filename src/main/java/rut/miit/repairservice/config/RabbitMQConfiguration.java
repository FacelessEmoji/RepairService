package rut.miit.repairservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

    public static final String EXCHANGE_NAME = "spring-boot-exchange";
    public static final String ORDER_PRICE_QUEUE = "order-price-queue";
    public static final String ORDER_PARTS_QUEUE = "order-parts-queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue orderPriceQueue() {
        return new Queue(ORDER_PRICE_QUEUE, true);
    }

    @Bean
    public Queue orderPartsQueue() {
        return new Queue(ORDER_PARTS_QUEUE, true);
    }

    @Bean
    public Binding priceBinding(Queue orderPriceQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderPriceQueue).to(exchange).with("order.price");
    }

    @Bean
    public Binding partsBinding(Queue orderPartsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderPartsQueue).to(exchange).with("order.parts");
    }
}

