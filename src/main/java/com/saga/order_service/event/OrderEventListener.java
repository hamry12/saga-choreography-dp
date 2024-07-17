package com.saga.order_service.event;

import com.saga.order_service.entity.PurchaseOrder;
import com.saga.order_service.repository.OrderPurchaseRepository;
import org.saga.dto.PaymentRequestDTO;
import org.saga.event.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @Autowired
    private OrderPurchaseRepository orderPurchaseRepository;

    @RabbitListener(queues = "payment-queue")
    public void handlePaymentStatus(Integer orderId){
        System.out.println("## handlePaymentStatus ##");
        PurchaseOrder order=orderPurchaseRepository.findById(orderId).orElseThrow();
        order.setOrderStatus(OrderStatus.ORDER_COMPLETED);
        orderPurchaseRepository.save(order);
    }
}
