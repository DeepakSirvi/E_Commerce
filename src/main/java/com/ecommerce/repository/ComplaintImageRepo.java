package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.ComplaintImage;

public interface ComplaintImageRepo extends JpaRepository<ComplaintImage, String> {

}
