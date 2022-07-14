package com.microservice.hibernate.repository.Order;

import com.microservice.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepositoryCustom {
    public List<Order> findOrdersByCustomerEmail(String firstName
    );

}

