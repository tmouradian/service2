package com.microservice.hibernate.controller;

import com.microservice.hibernate.dto.OrderData;
import com.microservice.hibernate.services.OrderService;
import com.microservice.hibernate.mappers.OrderRequestMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource(name = "orderService")
    private OrderService orderService;

    /**
     * <p>Get all order data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<OrderData>
     */
    @GetMapping
    public List <OrderData> getOrders() {
        return orderService.getAllOrders();
    }

    /**
     * Method to get the order data based on the ID.
     * @param email
     * @return CustomerData
     */
    @GetMapping("/customer")
    public  List <OrderData> getOrdersByCustomerEmail(@RequestParam(name = "email") String email) {
        return orderService.getOrdersByCustomerEmail(email);
    }

    /**
     * Method to get the order data based on the ID.
     * @param orderId
     * @return OrderRequestMapper
     */
    @GetMapping("/bulk")
    public  OrderRequestMapper getOrderBulkByOrderId(@RequestParam(name = "orderId") Long orderId) {
        return orderService.getOrderBulkByOrderId(orderId);
    }

    /**
     * Method to get the order data based on the ID.
     * @param id
     * @return CustomerData
     */
    @GetMapping("/order/{id:\\d+}")
    public OrderData getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    /**
     * Post request to create customer information int the system.
     * @param orderData
     * @return
     */
    @PostMapping("/create")
    public OrderData saveOrder(final @RequestBody OrderData orderData) {
        return orderService.saveOrder(orderData);
    }

    /**
     * Post request to create customer information int the system.
     * @param order
     * @return
     */
    @PostMapping(path = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderData saveOrderBulk(final @RequestBody OrderRequestMapper order) {
        return orderService.saveOrderBulk(order);
    }

//    /**
//     * <p>Delete customer from the system based on the ID. The method mapping is like the getCustomer with difference of
//     * @DeleteMapping and @GetMapping</p>
//     * @param id
//     * @return
//     */
//    @DeleteMapping("/order/{id:\\d+}")
//    public Boolean deleteOrder(@PathVariable Long id) {
//        return orderService.deleteCustomer(id);
//    }


}