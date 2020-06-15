package com.curtisnewbie.message;

import javax.jms.JMSException;
import com.curtisnewbie.model.Order;

public interface OrderReceiverMessagingService {

    Order receiveOrder() throws JMSException;

    Order receiveAndConvertOrder() throws JMSException;

    void listenOrder(Order order) throws JMSException;
}
