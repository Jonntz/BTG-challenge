package com.jonatas.ordermsg.listeners;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.support.MessageBuilder;

import com.jonatas.ordermsg.factory.OrderCreatedEventFactory;
import com.jonatas.ordermsg.services.OrderService;


@ExtendWith(MockitoExtension.class)
public class OrderCreatedListenerTest {

  @Mock
  OrderService orderService;

  @InjectMocks
  OrderCreatedListener orderCreatedListener;

  @Nested
  class Listen {
    @Test
    void ShouldCallServiceWithCorrectParameters() {
      var event = OrderCreatedEventFactory.build();
      var message = MessageBuilder.withPayload(event).build();

      orderCreatedListener.listen(message);

      verify(orderService, times(1)).save(eq(message.getPayload()));
    }
  }
}
