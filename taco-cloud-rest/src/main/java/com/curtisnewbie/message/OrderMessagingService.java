package com.curtisnewbie.message;

import com.curtisnewbie.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderMessagingService {

    // <K, V> where K is the type of topic/key, and V is the type of the payload, which is Order
    private KafkaTemplate<String, Order> kafkaTemplate;

    public OrderMessagingService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(Order order) {
        // we can also use default topic specified in .yml
        kafkaTemplate.send("tacocloud.orders.topic", order);
    }

    public void sendOrderWithDefaultTopic(Order order) {
        kafkaTemplate.sendDefault(order);
    }
}
