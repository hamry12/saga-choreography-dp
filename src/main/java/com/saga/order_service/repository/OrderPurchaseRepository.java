package com.saga.order_service.repository;

import com.saga.order_service.entity.PurchaseOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderPurchaseRepository extends JpaRepository<PurchaseOrder, Integer> {
}
