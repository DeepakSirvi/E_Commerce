package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.OrderHistory;

public interface OrderHistoryRepo extends JpaRepository<OrderHistory, String> {

}
