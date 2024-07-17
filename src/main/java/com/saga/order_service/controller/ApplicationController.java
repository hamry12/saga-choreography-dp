package com.saga.order_service.controller;

import com.saga.order_service.repository.OrderPurchaseRepository;
import com.saga.order_service.service.OrderService;
import org.saga.dto.OrderRequestDTO;
import org.saga.dto.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class ApplicationController {

    private OrderService orderService;

    @Autowired
    public ApplicationController(OrderService orderService){
        this.orderService=orderService;
    }

    @PostMapping("/create")
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.createOrder(orderRequestDTO);
    }

}
