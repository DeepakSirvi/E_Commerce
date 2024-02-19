package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.PromoCode;

public interface PromoCodeRepo extends JpaRepository<PromoCode, String> {

}
