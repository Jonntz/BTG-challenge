package com.jonatas.ordermsg.factory;

import java.math.BigDecimal;
import java.util.List;

import com.jonatas.ordermsg.entities.OrderEntity;
import com.jonatas.ordermsg.entities.OrderItemsEntity;

public class OrderEntityFactory {

  public static OrderEntity build(){
    var items = new OrderItemsEntity("notebook", 1, BigDecimal.valueOf(20.50));
    var entity = new OrderEntity();

    entity.setOrderId(1L);
    entity.setCostumerId(2L);
    entity.setTotal(BigDecimal.valueOf(20.50));

    entity.setItems(List.of(items));

    return entity;
  }
}
