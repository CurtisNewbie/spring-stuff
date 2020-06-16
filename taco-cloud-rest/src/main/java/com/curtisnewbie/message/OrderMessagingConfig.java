package com.curtisnewbie.message;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMessagingConfig {

    // meaning less constants to show how to add properties to messages
    public static final String X_ORDER_SOURCE = "X_ORDER_SOURCE";
    public static final String WEB_SOURCE = "WEB";

    @Bean
    public MessageConverter messageConverter() {
        // MessageConverter for ampq using Jackson 2
        return new Jackson2JsonMessageConverter();
    }


}
