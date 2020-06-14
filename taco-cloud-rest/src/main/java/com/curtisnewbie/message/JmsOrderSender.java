package com.curtisnewbie.message;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import com.curtisnewbie.model.Order;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsOrderSender implements OrderSenderMessagingService {

    private JmsTemplate jms;
    private Destination orderDestination;

    public JmsOrderSender(JmsTemplate jms, Destination orderDestination) {
        this.jms = jms;
        this.orderDestination = orderDestination;
    }

    @Override
    public void sendOrderToDefaultDest(Order order) {
        jms.send((session) -> {
            // without specifying destination, the default one will be used
            // default destination is defined in configuration file
            Message msg = session.createObjectMessage(order);
            // we can add extra properties
            msg.setStringProperty(OrderMessagingConfig.X_ORDER_SOURCE,
                    OrderMessagingConfig.WEB_SOURCE);
            return msg;
        });
    }

    @Override
    public void sendOrderToConfigDest(Order order) {
        // we can also produce and inject a bean representing the destination
        jms.send(orderDestination, (session) -> session.createObjectMessage(order));
    }

    @Override
    public void sendOrderToStrDest(Order order) {
        // we can also use a String to represent the destination directly
        jms.send("tacocloud.order.queue", (session) -> session.createObjectMessage(order));
    }

    @Override
    public void convertAndSendOrder(Order order) {
        // Spring will use the default MessageConverter to convert the message
        // we can custom which MessageConverter to use, see
        // OrderMessagingConfig#messageConverter()
        jms.convertAndSend(orderDestination, order);
    }

    @Override
    public void convertAndSendOrderWithProp(Order order) {
        // when we want to add extra properties, we cannot add them like in
        // JmsOrderMessaging#sendOrderToDefaultDest, we need post-processor
        jms.convertAndSend(orderDestination, order, this::postProcessMsg);
    }

    private Message postProcessMsg(Message msg) throws JMSException {
        msg.setStringProperty(OrderMessagingConfig.X_ORDER_SOURCE, OrderMessagingConfig.WEB_SOURCE);
        return msg;
    }
}

