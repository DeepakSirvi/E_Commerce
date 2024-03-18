package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Tags;

public interface TagsRepo extends JpaRepository<Tags, String> {

}
