package com.microservice.hibernate.repository.OrderItem;

import com.microservice.entity.OrderItem;
import com.microservice.hibernate.repository.Order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    @Autowired
    @Lazy
    OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager em;

    public List<OrderItem> findOrderItemsByOrderId(Long orderId) {
        TypedQuery query1 = em.createQuery("select o from OrderItem o where o.orderId = ?1", OrderItem.class);
        query1.setParameter(1, orderId);
        List<OrderItem> oiList = query1.getResultList();

        return oiList;
    }

}
