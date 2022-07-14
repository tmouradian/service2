package com.microservice.hibernate.services;

import com.microservice.entity.OrderItem;
import com.microservice.hibernate.dto.OrderItemData;
import com.microservice.hibernate.mappers.OrderRequestMapper;
import com.microservice.hibernate.repository.OrderItem.OrderItemRepository;
import com.microservice.hibernate.repository.Order.OrderRepository;
import com.microservice.hibernate.repository.Order.OrderRepositoryCustom;
import com.microservice.entity.Order;
import com.microservice.hibernate.dto.OrderData;
import com.microservice.hibernate.repository.OrderItem.OrderItemRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service("orderService")
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderRepositoryCustom orderRepositoryCustom;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemRepositoryCustom orderItemRepositoryCustom;



    /**
     * Create a customer based on the data sent to the service class.
     * @param order
     * @return DTO representation of the customer
     */
    public OrderData saveOrder(OrderData order) {
        Order orderModel = populateOrderEntity(order);
        return populateOrderData(orderRepository.save(orderModel));
    }

    /**
     * Create a customer based on the data sent to the service class.
     * @param order
     * @return DTO representation of the customer
     */
    public OrderData saveOrderBulk(OrderRequestMapper order) {
        //save order
        Order orderModel = populateOrderEntity(order.getOrderData());
        OrderData OrderDataPersisted = populateOrderData(orderRepository.save(orderModel));

        //save orderItems
        List<OrderItemData> orderItemList = order.getOrderItems();
        orderItemList.forEach(orderItemData -> {
            OrderItem oi = populateOrderItemEntity(orderItemData);
            oi.setOrderId( OrderDataPersisted.getId() );
            orderItemRepository.save(oi);
        });

        return OrderDataPersisted;
    }


    /**
     * Method to return the list of all the customers in the system.This is a simple
     * implementation but use pagination in the real world example.
     * @return list of customer
     */
    public List <OrderData> getAllOrders() {
        List <OrderData> orders = new ArrayList < > ();
        List <Order> orderList = orderRepository.findAll();
        orderList.forEach(order -> {
            orders.add(populateOrderData(order));
        });
        return orders;
    }

    /**
     * Get order orderId ID. The service will send the order data else will throw the exception.
     * @param orderId
     * @return OrderData.java
     */
    public OrderData getOrderById(Long orderId) {
        return populateOrderData(orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found")));
    }

    /**
     * Get customer by name. The service will send the customer data else will throw the exception.
     * @param email
     * @return OrderData.java
     */
    public List <OrderData> getOrdersByCustomerEmail(String email) {
        List <OrderData> orders = new ArrayList < > ();
        List <Order> orderList;
        try{
            orderList = orderRepositoryCustom.findOrdersByCustomerEmail(email);
        }catch(Exception e){
            throw e;
        }
        orderList.forEach(order -> {
            orders.add(populateOrderData(order));
        });

        return orders;
    }


    /**
     * Get customer by name. The service will send the customer data else will throw the exception.
     * @param orderId
     * @return OrderData.java
     */
    public OrderRequestMapper getOrderBulkByOrderId(Long orderId) {

        OrderRequestMapper fullOrder = new OrderRequestMapper();

        //order
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        OrderData orderData = populateOrderData(order);

        //orderItems
        List <OrderItemData> orderItemDataList = new ArrayList < > ();
        List <OrderItem> orderItemList;
        try{
            orderItemList = orderItemRepositoryCustom.findOrderItemsByOrderId(orderId);
        }catch(Exception e){
            throw e;
        }

        orderItemList.forEach(orderItem -> {
            orderItemDataList.add(populateOrderItemData(orderItem));
        });


        //setting fullOrder
        fullOrder.setOrderItems(orderItemDataList);
        fullOrder.setOrderData(orderData);


        return fullOrder;
    }


    /**
     * Internal method to convert Order JPA entity to the DTO object
     * for frontend data
     * @param order
     * @return CustomerData.java
     */
    private OrderData populateOrderData(final Order order) {
        OrderData orderData = new OrderData();
        orderData.setId(order.getId());
        orderData.setOrderDate(order.getOrderDate());
        orderData.setCustomerId(order.getCustomerId());
        return orderData;
    }

    /**
     * Method to map the front end order object to the JPA customer entity.
     * @param orderData
     * @return Customer
     */
    private Order populateOrderEntity(OrderData orderData) {
        Order order = new Order();
        order.setCustomerId(orderData.getCustomerId());
        return order;
    }



    //External DTOS
    /**
     * Internal method to convert OrderItem JPA entity to the DTO object
     * for frontend data
     * @param orderItem
     * @return CustomerData.java
     */
    private OrderItemData populateOrderItemData(final OrderItem orderItem) {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(orderItem.getId());
        orderItemData.setOrderId(orderItem.getOrderId());
        orderItemData.setQuantity(orderItem.getQuantity());
        orderItemData.setProductId(orderItem.getProductId());

        return orderItemData;
    }


    //External Entities
    /**
     * Method to map the front end orderItem object to the JPA customer entity.
     * @param orderItemData
     * @return Customer
     */
    private OrderItem populateOrderItemEntity(OrderItemData orderItemData) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(orderItemData.getProductId());
        orderItem.setQuantity(orderItemData.getQuantity());
        orderItem.setOrderId(orderItemData.getOrderId());

        return orderItem;
    }

}