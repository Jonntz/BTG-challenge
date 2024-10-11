package com.jonatas.ordermsg.factory;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.jonatas.ordermsg.controllers.dto.OrderResponseDTO;

public class OrderResponseFactory {

  public static Page<OrderResponseDTO> buildWithOneItem(){
    var orderResponse = new OrderResponseDTO(1L, 2L, BigDecimal.valueOf(20.50));

    return new PageImpl<>(List.of(orderResponse));
  }
}
