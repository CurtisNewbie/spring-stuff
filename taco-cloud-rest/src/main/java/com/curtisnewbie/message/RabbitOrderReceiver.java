package com.curtisnewbie.message;

import javax.jms.Message;
import com.curtisnewbie.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderReceiver {

    private static final Logger log = LoggerFactory.getLogger(RabbitOrderReceiver.class);
    private RabbitTemplate rabbitTemplate;

    public RabbitOrderReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // again a pull-based way to receive msgs, i.e., a blocking way, but it returns immediately,
    // because
    // it doesn't specify how long it waits, the default time for waiting/blocking is 0ms
    public Order receiveAndReturn() {
        // using default routing key
        return (Order) rabbitTemplate.receiveAndConvert();
    }

    public Order receiveAndWait(long ms) {
        // block and wait for a maximum of 'ms' miliseconds
        // this can also be configured in .yml file
        return (Order) rabbitTemplate.receiveAndConvert(ms);
    }

    @RabbitListener
    public void listenOrder(Order order) {
        // push-based way to receive msgs
        log.info("Received Order: {}", order.toString());
    }
}

