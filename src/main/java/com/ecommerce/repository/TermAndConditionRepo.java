package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.TermsAndCondition;

public interface TermAndConditionRepo extends JpaRepository<TermsAndCondition, String> {

}
