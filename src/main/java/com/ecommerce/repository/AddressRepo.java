package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.Address;
import com.ecommerce.model.User;

public interface AddressRepo extends JpaRepository<Address, Long> {

	public Optional<Address> findByIdAndStatus(Long id, boolean b);

//	public Address findByUserAddress(User user);

	

	@Query("SELECT a FROM Address a WHERE a.userAddress.id=:uid")
	public List<Address> findAddresssByuserId(Long uid);

    @Query("SELECT a FROM Address a WHERE a.userAddress.id=:id AND a.status=true")
	public List<Address> getActiveAddressOfUser(Long id);
}
