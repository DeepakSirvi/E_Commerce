package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;

public interface VarientRepo extends JpaRepository<Varient, String> {

	public boolean existsByVarientName(String varientName);

	public Optional<Varient> findByProductId(String id);

	public List<Varient> findByProductIdAndStatus(String id, Status active, Pageable pageable);


}
