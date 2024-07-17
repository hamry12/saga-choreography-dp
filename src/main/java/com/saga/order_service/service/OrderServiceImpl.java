package com.saga.order_service.service;

import com.saga.order_service.entity.PurchaseOrder;
import com.saga.order_service.repository.OrderPurchaseRepository;

import org.saga.dto.OrderResponseDTO;
import org.saga.dto.OrderRequestDTO;
import org.saga.event.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderPurchaseRepository orderPurchaseRepository;

    @Autowired
    public OrderServiceImpl(OrderPurchaseRepository orderPurchaseRepository){
        this.orderPurchaseRepository=orderPurchaseRepository;
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        PurchaseOrder purchaseOrder=orderPurchaseRepository.save(convertEntityDTO(orderRequestDTO));
        rabbitTemplate.convertAndSend(purchaseOrder.getId());
        return convertEntityOrderRespDTO(purchaseOrder);
    }

    private OrderResponseDTO convertEntityOrderRespDTO(PurchaseOrder purchaseOrder){
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderStatus(purchaseOrder.getOrderStatus());
        orderResponseDTO.setAmount(purchaseOrder.getAmount());
        orderResponseDTO.setProductId(purchaseOrder.getProductId());
        orderResponseDTO.setUserId(purchaseOrder.getUserId());
        orderResponseDTO.setOrderId(purchaseOrder.getId());
        return orderResponseDTO;
    }

    private PurchaseOrder convertEntityDTO(OrderRequestDTO orderRequestDTO){
        PurchaseOrder purchaseOrder= new PurchaseOrder();
        purchaseOrder.setUserId(orderRequestDTO.getUserId());
        purchaseOrder.setAmount(orderRequestDTO.getAmount());
        purchaseOrder.setProductId(orderRequestDTO.getProductId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        return purchaseOrder;
    }
}
