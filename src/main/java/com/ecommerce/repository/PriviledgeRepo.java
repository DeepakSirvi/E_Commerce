package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Priviledges;

public interface PriviledgeRepo extends JpaRepository<Priviledges, String> {

}
