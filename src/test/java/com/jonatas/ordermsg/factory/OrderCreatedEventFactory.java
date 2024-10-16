package com.jonatas.ordermsg.factory;

import java.math.BigDecimal;
import java.util.List;

import com.jonatas.ordermsg.listeners.dto.OrderCreatedEvent;
import com.jonatas.ordermsg.listeners.dto.OrderItemEvent;

public class OrderCreatedEventFactory {
  public static OrderCreatedEvent build(){
    var items = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
    var event = new OrderCreatedEvent(1L, 2L, List.of(items));

    return event;
  }

  public static OrderCreatedEvent buildWithListOfItems(){
    var item = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
    var item2 = new OrderItemEvent("mouse", 1, BigDecimal.valueOf(45.50));
    var event = new OrderCreatedEvent(1L, 2L, List.of(item, item2));

    return event;
  }
}
