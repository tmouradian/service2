package com.microservice.hibernate.repository.Order;

import com.microservice.entity.Customer;
import com.microservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @Autowired
    @Lazy
    OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager em;

    public List<Order> findOrdersByCustomerEmail(String email) {
        TypedQuery query1 = em.createQuery("select c from Customer c where c.email = ?1", Customer.class);
        query1.setParameter(1, email);
        Customer c = (Customer) query1.getResultList().get(0);

        Long cusId = c.getId();
        TypedQuery query2 = em.createQuery("select o from Order o where o.customerId = ?1", Order.class);
        query2.setParameter(1, cusId);

        return query2.getResultList();
    }

    @Transactional
    public void deleteByCustomerFirstName(String firstName) {
        Query query = em.createQuery("delete from Order c where c.firstName = ?1");
        query.setParameter(1, firstName);

        query.executeUpdate();

    }
}
