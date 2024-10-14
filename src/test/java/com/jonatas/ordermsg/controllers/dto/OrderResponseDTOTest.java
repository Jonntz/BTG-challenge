package com.jonatas.ordermsg.controllers.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.jonatas.ordermsg.factory.OrderEntityFactory;

public class OrderResponseDTOTest {
  
  @Nested
  class FromEntity {
    @Test
    void sholdMapCorrectly(){
      // arrange
      var entity = OrderEntityFactory.build();

      // act
      var response = OrderResponseDTO.fromEntity(entity);

      // assert
      assertEquals(entity.getOrderId(), response.orderId());
      assertEquals(entity.getCostumerId(), response.costumerId());
      assertEquals(entity.getTotal(), response.total() );
    }
  }
}
