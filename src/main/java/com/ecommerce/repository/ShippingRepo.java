package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Shipping;

public interface ShippingRepo extends JpaRepository<Shipping, String> {

}
