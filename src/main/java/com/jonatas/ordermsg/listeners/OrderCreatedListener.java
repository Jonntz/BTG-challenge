package com.jonatas.ordermsg.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.jonatas.ordermsg.listeners.dto.OrderCreatedEvent;
import com.jonatas.ordermsg.services.OrderService;

@Component
public class OrderCreatedListener {

  private static final String ORDER_CREATED_QUEUE = "btg-pactual-order-created";
  private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);
  private final OrderService orderService;

  public OrderCreatedListener(OrderService orderService) {
    this.orderService = orderService;
  }

  @RabbitListener(queues = ORDER_CREATED_QUEUE)
  public void listen(Message<OrderCreatedEvent> message){
    logger.info("message consumed: {}", message);
    orderService.save(message.getPayload());
  }
}
