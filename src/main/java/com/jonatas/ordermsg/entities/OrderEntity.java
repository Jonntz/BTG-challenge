package com.jonatas.ordermsg.entities;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "orders")
public class OrderEntity {

  @MongoId
  private Long orderId;

  @Indexed(name = "costumer_id_index")
  private Long costumerId;

  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal total;

  private List<OrderItemsEntity> items;


  public OrderEntity() {
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Long getCostumerId() {
    return costumerId;
  }

  public void setCostumerId(Long costumerId) {
    this.costumerId = costumerId;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public List<OrderItemsEntity> getItems() {
    return items;
  }

  public void setItems(List<OrderItemsEntity> items) {
    this.items = items;
  }


  
}
