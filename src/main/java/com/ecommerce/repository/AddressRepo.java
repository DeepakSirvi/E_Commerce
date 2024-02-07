package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.Address;

public interface AddressRepo extends JpaRepository<Address, String> {

	public Optional<Address> findByIdAndStatus(String id, boolean b);

//	public Address findByUserAddress(User user);

	@Query("SELECT a FROM Address a WHERE a.userAddress.id=:uid")
	public List<Address> findAddresssByuserId(String uid);

	@Query("SELECT a FROM Address a WHERE a.userAddress.id=:id AND a.status=true")
	public List<Address> getActiveAddressOfUser(String id);
}
