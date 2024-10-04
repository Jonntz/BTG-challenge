package com.jonatas.ordermsg.services;

import java.math.BigDecimal;
import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.jonatas.ordermsg.controllers.dto.OrderResponseDTO;
import com.jonatas.ordermsg.entities.OrderEntity;
import com.jonatas.ordermsg.entities.OrderItemsEntity;
import com.jonatas.ordermsg.listeners.dto.OrderCreatedEvent;
import com.jonatas.ordermsg.repositories.OrderRepository;

@Service
public class OrderService {
  
  private final OrderRepository orderRepository;
  private final MongoTemplate mongoTemplate;

  public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
    this.orderRepository = orderRepository;
    this.mongoTemplate = mongoTemplate;
  }

  public void save(OrderCreatedEvent event){
    var entity = new OrderEntity();
    entity.setOrderId(event.codigoPedido());
    entity.setCostumerId(event.codigoCliente());

    entity.setItems(getOrderItems(event));
    entity.setTotal(getTotal(event));

    orderRepository.save(entity);
  }

  public Page<OrderResponseDTO> findAllByCostumerId(Long costumerId, PageRequest pageRequest){
    var orders = orderRepository.findAllByCostumerId(costumerId, pageRequest);

    return orders.map(OrderResponseDTO::fromEntity);
  }

  
  public BigDecimal findTotalOnOrdersByCostumerId(Long costumerId){
    var aggregations = newAggregation(
       match(Criteria.where("costumerId").is(costumerId)),
       group().sum("total").as("total")
    );

    var response =  mongoTemplate.aggregate(aggregations, "orders", Document.class);


    return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
  }

  private BigDecimal getTotal(OrderCreatedEvent event) {
    return event
            .itens()
            .stream()
            .map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
  }

  private static List<OrderItemsEntity> getOrderItems(OrderCreatedEvent event){
    return event
      .itens()
      .stream()
      .map(item -> new OrderItemsEntity(
        item.produto(), 
        item.quantidade(), 
        item.preco())).toList();
  }
}
