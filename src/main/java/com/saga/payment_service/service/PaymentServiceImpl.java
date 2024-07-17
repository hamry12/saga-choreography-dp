package com.saga.payment_service.service;

import com.saga.payment_service.entity.PaymentDetails;
import com.saga.payment_service.repository.PaymentRepository;
import org.saga.dto.PaymentRequestDTO;
import org.saga.dto.PaymentResponseDTO;
import org.saga.event.PaymentStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository){
        this.paymentRepository=paymentRepository;
    }


    @Override
    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO) {
        PaymentDetails save = paymentRepository.save(convertEntityDTO(paymentRequestDTO));
        rabbitTemplate.convertAndSend(paymentRequestDTO.getOrderId());
        return convertEntityRespDTO(save);
    }

    @RabbitListener(queues = "order-queues")
    public void handleOrderEvent(Integer orderId){
        System.out.println("## handleOrderEvent ##");
        PaymentDetails paymentDetails=paymentRepository.findById(orderId).orElseThrow();
        paymentDetails.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);
        paymentRepository.save(paymentDetails);
        rabbitTemplate.convertAndSend(orderId);
    }


    private PaymentResponseDTO convertEntityRespDTO(PaymentDetails save){
        PaymentResponseDTO paymentResponseDTO= new PaymentResponseDTO();
        paymentResponseDTO.setAmount(save.getAmount());
        paymentResponseDTO.setPaymentStatus(save.getPaymentStatus());
        paymentResponseDTO.setOrderId(save.getOrderId());
        paymentResponseDTO.setUserId(save.getUserId());
        paymentResponseDTO.setPaymentId(save.getId());
        return paymentResponseDTO;
    }

    private PaymentDetails convertEntityDTO(PaymentRequestDTO paymentRequestDTO) {
        PaymentDetails paymentDetails= new PaymentDetails();
        paymentDetails.setOrderId(paymentRequestDTO.getOrderId());
        paymentDetails.setAmount(paymentRequestDTO.getAmount());
        paymentDetails.setUserId(paymentRequestDTO.getUserId());
        paymentDetails.setPaymentStatus(PaymentStatus.PAYMENT_PROCESSED);
        return paymentDetails;
    }
}
