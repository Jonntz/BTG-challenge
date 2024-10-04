package com.jonatas.ordermsg.controllers.dto;

import java.math.BigDecimal;

import com.jonatas.ordermsg.entities.OrderEntity;

public record OrderResponseDTO(Long orderId, Long costumerId, BigDecimal total) {
  public static OrderResponseDTO fromEntity(OrderEntity entity){
    return new OrderResponseDTO(entity.getOrderId(), entity.getCostumerId(), entity.getTotal());
  }
}
