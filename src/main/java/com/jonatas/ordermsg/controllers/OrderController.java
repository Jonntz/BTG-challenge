package com.jonatas.ordermsg.controllers;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jonatas.ordermsg.controllers.dto.ApiResponseDTO;
import com.jonatas.ordermsg.controllers.dto.OrderResponseDTO;
import com.jonatas.ordermsg.controllers.dto.PaginationResponseDTO;
import com.jonatas.ordermsg.services.OrderService;


@RestController

public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/costumers/{costumerId}/orders")
  public ResponseEntity<ApiResponseDTO<OrderResponseDTO>> listOrders(
    @PathVariable("costumerId") Long costumerId,
    @RequestParam(name = "page", defaultValue = "0") Integer page,
    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

      var pageResponse = orderService.findAllByCostumerId(costumerId, PageRequest.of(page, pageSize));
      var totalOnOrders = orderService.findTotalOnOrdersByCostumerId(costumerId);
      
      return ResponseEntity.ok(new ApiResponseDTO<>(
        Map.of("totalOnOrders", totalOnOrders),
        pageResponse.getContent(),
        PaginationResponseDTO.fromPage(pageResponse)
      ));
  }
  
  
}
