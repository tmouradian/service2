package com.microservice.hibernate.mappers;
import com.microservice.entity.OrderItem;
import com.microservice.hibernate.dto.OrderData;
import com.microservice.hibernate.dto.OrderItemData;

import java.beans.Transient;
import java.util.List;

public class OrderRequestMapper {

    private Long orderId;
    private Long customerId;

    private List<OrderItemData> orderItems;


    //getters setters
    public void setOrderId(Long orderId){
        this.orderId = orderId;
    }
    public Long getOrderId(){
        return this.orderId;
    }

    public void setCustomerId(Long customerId){
        this.customerId = customerId;
    }
    public Long getCustomerId(){
        return this.customerId;
    }

    public void setOrderItems(List<OrderItemData> orderItems){
        this.orderItems = orderItems;
    }
    public List<OrderItemData> getOrderItems(){
        return this.orderItems;
    }


    //set sub entities
    public void setOrderData(final OrderData orderData){
        this.orderId = orderData.getId();
        this.customerId = orderData.getCustomerId();
    }

    //get sub entities
    @Transient
    public OrderData getOrderData(){
         OrderData o = new OrderData();
        o.setCustomerId(this.getCustomerId());

        OrderItem oi = new OrderItem();
        oi.setOrderId(2L);
        return o;
    }


}