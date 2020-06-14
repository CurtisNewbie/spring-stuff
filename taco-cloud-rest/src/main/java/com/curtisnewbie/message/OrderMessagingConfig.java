package com.curtisnewbie.message;

import java.util.*;
import javax.jms.Destination;
import com.curtisnewbie.model.Order;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

public class OrderMessagingConfig {

    // meaning less constants to show how to add properties to messages
    public static final String X_ORDER_SOURCE = "X_ORDER_SOURCE";
    public static final String WEB_SOURCE = "WEB";

    @Bean
    public Destination orderQueue() {
        return new ActiveMQQueue("tacocloud.order.queue");
    }

    // this method specifies using MappingJackson2MessageConverter, the default is
    // SimpleMessageConverter
    // @Bean
    public MappingJackson2MessageConverter messageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId"); // default is none, this property
                                                           // specifies what the message is
        // the correct value (class name) used in this property is set automatically,
        // but this is inflexible, we can specify it on our own
        Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
        typeIdMappings.put("order", Order.class); // now the "_typeId" will be using "order" for
                                                  // Order.class
        messageConverter.setTypeIdMappings(typeIdMappings);
        return messageConverter;
    }
}
