package com.saga.order_service.service;

import org.saga.dto.OrderResponseDTO;
import org.saga.dto.OrderRequestDTO;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

}
