package com.curtisnewbie.message;

import com.curtisnewbie.model.Order;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
public class RabbitOrderMessagingService {

    private final String ROUTING_KEY = "tacocloud.order";
    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter msgCvter;

    public RabbitOrderMessagingService(RabbitTemplate rabbitTemplate, MessageConverter msgCvter) {
        this.rabbitTemplate = rabbitTemplate;
        this.msgCvter = msgCvter;
    }

    public void sendOrder(Order order) {
        // differs from JMS, in JMS we setStringProperty(),
        // but in amqp, we use MessageProperties object
        MessageProperties props = new MessageProperties();
        props.setHeader(OrderMessagingConfig.X_ORDER_SOURCE, OrderMessagingConfig.WEB_SOURCE); // random
                                                                                               // header
        // here we convert Object to Message using the default MessageProperties
        Message msg = msgCvter.toMessage(order, props);

        // using default exchange (which has a name as well) and the specified routing key, they can
        // be configured in .yml file
        rabbitTemplate.send(ROUTING_KEY, msg);
    }

    public void convertAndSend(Order order) {
        // one-line as in JMS
        rabbitTemplate.convertAndSend(ROUTING_KEY, order);
    }
}
