package com.ecommerce.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.model.Product;
import com.ecommerce.model.SubCategory;

public interface ProductRepo extends JpaRepository<Product, Long> {

	public Page<Product> findBySubCategoryAndListingStatus(SubCategory category, boolean b, Pageable pageable);

	public Page<Product> findByListingStatus(boolean b, Pageable pageable);

	public boolean existsByProductName(String productName);
      

}
