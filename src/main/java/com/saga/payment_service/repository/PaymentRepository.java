package com.saga.payment_service.repository;

import com.saga.payment_service.entity.PaymentDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetails, Integer> {
}
