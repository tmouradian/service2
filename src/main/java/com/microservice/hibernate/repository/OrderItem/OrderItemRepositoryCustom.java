package com.microservice.hibernate.repository.OrderItem;

import com.microservice.entity.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepositoryCustom {
    public List<OrderItem> findOrderItemsByOrderId(Long orderId);
}

