package com.curtisnewbie.message;

import com.curtisnewbie.model.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMesssagingListener {

    private static final Logger log = LoggerFactory.getLogger(OrderMesssagingListener.class);

    @KafkaListener(topics = "tacocloud.orders.topic")
    public void listenOrder(Order order) {
        log.info("Received Order: {}", order.toString());
    }

    // to get extra information about the message
    @KafkaListener(topics = "tacocloud.orders.topic")
    public void listenOrderComsumerRecord(ConsumerRecord<String, Order> cr) {
        Order order = cr.value();
        log.info("Received Order: {}", order.toString());
    }
}
