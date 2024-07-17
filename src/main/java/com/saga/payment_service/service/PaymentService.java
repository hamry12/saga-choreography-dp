package com.saga.payment_service.service;

import org.saga.dto.PaymentRequestDTO;
import org.saga.dto.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO);
}
