package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Complaint;

public interface ComplaintRepo  extends JpaRepository<Complaint, String>{

}
