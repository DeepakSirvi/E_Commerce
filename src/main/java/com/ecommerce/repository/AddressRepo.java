package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Address;
import com.ecommerce.model.User;

public interface AddressRepo extends JpaRepository<Address, Long> {

	public Optional<Address> findByIdAndIsDeleted(Long id, boolean b);

	public Address findByUser(User user);

	public Address findByStatus(String string);
}
