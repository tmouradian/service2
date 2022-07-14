package com.microservice.hibernate.dto;
import java.sql.Timestamp;

public class OrderData {
    private Long id;
    private Long customerId; //foreign ID
    private Timestamp orderDate;


    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

    public void setCustomerId(Long customerId){
        this.customerId = customerId;
    }

    public Long getCustomerId(){
        return this.customerId;
    }

    public void setOrderDate(Timestamp orderDate){
        this.orderDate = orderDate;
    }

    public Timestamp getOrderDate(){
        return this.orderDate;
    }

}