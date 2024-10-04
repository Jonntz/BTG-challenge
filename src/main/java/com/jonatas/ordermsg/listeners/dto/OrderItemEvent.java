package com.jonatas.ordermsg.listeners.dto;

import java.math.BigDecimal;

public record OrderItemEvent(
  String produto,
  Integer quantidade,
  BigDecimal preco
) {

}
