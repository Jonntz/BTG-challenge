package com.jonatas.ordermsg.controllers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;

import com.jonatas.ordermsg.factory.OrderResponseFactory;
import com.jonatas.ordermsg.services.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

  @Mock
  OrderService orderService;

  @InjectMocks
  OrderController orderController;

  @Captor
  ArgumentCaptor<Long> costumerIdCaptor;

  @Captor
  ArgumentCaptor<PageRequest> pageRequestCaptor;

  @Nested
  class ListOrders {

    @Test
    void shouldReturnHttpOk() {
      // Arrange
      long costumerId = 1L;
      int page = 1;
      int pageSize = 10;
      doReturn(OrderResponseFactory.buildWithOneItem())
          .when(orderService).findAllByCostumerId(anyLong(), any());

      doReturn(BigDecimal.valueOf(20.50))
          .when(orderService).findTotalOnOrdersByCostumerId(anyLong());

      // Act
      var response = orderController.listOrders(costumerId, page, pageSize);

      // Assert
      assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    void shouldPassCorrectParametersToService() {
      // Arrange
      long costumerId = 1L;
      int page = 1;
      int pageSize = 10;
      doReturn(OrderResponseFactory.buildWithOneItem())
          .when(orderService).findAllByCostumerId(costumerIdCaptor.capture(), pageRequestCaptor.capture());

      doReturn(BigDecimal.valueOf(20.50))
          .when(orderService).findTotalOnOrdersByCostumerId(costumerIdCaptor.capture());

      // Act
      var response = orderController.listOrders(costumerId, page, pageSize);

      // Assert
      assertEquals(2, costumerIdCaptor.getAllValues().size());
      assertEquals(costumerId, costumerIdCaptor.getAllValues().get(0));
      assertEquals(costumerId, costumerIdCaptor.getAllValues().get(1));

      assertEquals(page, pageRequestCaptor.getValue().getPageNumber());
      assertEquals(pageSize, pageRequestCaptor.getValue().getPageSize());
    }

    @SuppressWarnings("null")
    @Test
    void shouldReturnResponseBodyCorrectly() {
      // Arrange
      long costumerId = 1L;
      int page = 1;
      int pageSize = 10;
      BigDecimal totalOnOrders = BigDecimal.valueOf(20.50);
      var pagination = OrderResponseFactory.buildWithOneItem();

      doReturn(pagination)
          .when(orderService).findAllByCostumerId(anyLong(), any());

      doReturn(totalOnOrders)
          .when(orderService).findTotalOnOrdersByCostumerId(anyLong());

      // Act
      var response = orderController.listOrders(costumerId, page, pageSize);

      // Assert
      assertNotNull(response);
      assertNotNull(response.getBody());
      assertNotNull(response.getBody().data());
      assertNotNull(response.getBody().pagination());
      assertNotNull(response.getBody().summary());

      assertEquals(totalOnOrders, response.getBody().summary().get("totalOnOrders"));

      assertEquals(pagination.getTotalElements(), response.getBody().pagination().totalElements());
      assertEquals(pagination.getTotalPages(), response.getBody().pagination().totalPages());
      assertEquals(pagination.getNumber(), response.getBody().pagination().page());
      assertEquals(pagination.getSize(), response.getBody().pagination().pageSize());

      assertEquals(pagination.getContent(), response.getBody().data());
    }
  }
}
