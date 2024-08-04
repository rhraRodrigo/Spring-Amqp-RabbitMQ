package com.rodrigoandrade.springamqp.order_service.repository;

import com.rodrigoandrade.springamqp.order_service.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
