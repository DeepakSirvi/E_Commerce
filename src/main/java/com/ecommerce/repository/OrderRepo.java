package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Orders;

public interface OrderRepo  extends JpaRepository<Orders, String>{

}
