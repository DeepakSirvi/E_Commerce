package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.TermDescription;

public interface TermDescriptionRepo extends JpaRepository<TermDescription, String> {

}
