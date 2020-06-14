package com.curtisnewbie.message;

import com.curtisnewbie.model.Order;

public interface OrderSenderMessagingService {

    /**
     * Send Order using JMS to default destination
     * 
     * @param order
     */
    void sendOrderToDefaultDest(Order order);

    /**
     * Send Order using JMS to a destination that is configured using Destination bean
     * 
     * @param order
     */
    void sendOrderToConfigDest(Order order);

    /**
     * Send Order Using JMS to a destination that is configured using String
     * 
     * @param order
     */
    void sendOrderToStrDest(Order order);

    /**
     * Send Order Using JMS without the need of MessageCreator
     * 
     * @param order
     */
    void convertAndSendOrder(Order order);

    /**
     * Send Order Using JMS without using MessageCreator. Property is added using post-processor
     * 
     * @param order
     */
    void convertAndSendOrderWithProp(Order order);
}
