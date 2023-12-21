package com.ecommerce.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.model.Product;
import com.ecommerce.model.SubCategory;
import com.ecommerce.model.VarientCategory;

public interface ProductRepo extends JpaRepository<Product, Long> {

	public Page<Product> findBySubCategoryAndListingStatus(SubCategory category, boolean b, Pageable pageable);

	public boolean existsByProductName(String productName);

	@Query("Select p from Product p where LOWER(p.productName) LIKE LOWER(concat('%', :searchTerm, '%'))")
	public Page<Product> findByProductDetail(@Param("searchTerm") String search, Pageable pageable);

//	public Page<Product> findByListingStatus(boolean b, Example<Product> example, Pageable pageable);
}
