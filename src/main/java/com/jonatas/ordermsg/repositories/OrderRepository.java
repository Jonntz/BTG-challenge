package com.jonatas.ordermsg.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jonatas.ordermsg.entities.OrderEntity;

public interface OrderRepository extends MongoRepository<OrderEntity, Long>{
  Page<OrderEntity> findAllByCostumerId(Long costumerId, PageRequest pageRequest);
}
