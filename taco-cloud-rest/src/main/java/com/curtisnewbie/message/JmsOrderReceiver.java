package com.curtisnewbie.message;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import com.curtisnewbie.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
public class JmsOrderReceiver implements OrderReceiverMessagingService {

    private static final Logger log = LoggerFactory.getLogger(JmsOrderReceiver.class);
    private final JmsTemplate jms;
    private final MessageConverter converter;
    private final Destination orderDestination;

    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter,
            Destination orderDestination) {
        this.jms = jms;
        this.converter = converter;
        this.orderDestination = orderDestination;
    }

    @Override
    public Order receiveOrder() throws JMSException {
        // this is a blocking operation, it uses a Pull way of handling messages
        Message message = jms.receive("tacocloud.order.queue");
        return (Order) converter.fromMessage(message);
    }

    @Override
    public Order receiveAndConvertOrder() throws JMSException {
        // this is a blocking operation, it uses a Pull way of handling messages
        return (Order) jms.receiveAndConvert(orderDestination);
    }

    @Override
    @JmsListener(destination = "tacocloud.order.queue")
    public void listenOrder(Order order) throws JMSException {
        // this is a Push way of handling messages, or i.e., async way of doing it
        log.info("Received Order: {}", order.toString());
    }
}
