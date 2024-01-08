package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.model.Product;
import com.ecommerce.model.Status;
import com.ecommerce.model.SubCategory;
import com.ecommerce.model.VarientCategory;

public interface ProductRepo extends JpaRepository<Product, String> {

	public Page<Product> findBySubCategoryAndListingStatus(SubCategory category, boolean b, Pageable pageable);

	public boolean existsByProductName(String productName);

	@Query("Select p from Product p where LOWER(p.productName) LIKE LOWER(concat('%', :searchTerm, '%'))")
	public Page<Product> findByProductDetail(@Param("searchTerm") String search, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE " + "(:productName is null OR p.productName LIKE %:productName%) "
			+ "AND p.listingStatus = :listingStatus AND p.verified = :status " + "GROUP BY p.id HAVING COUNT(p) > 0")
	Page<Product> findProductsByNameAndCriteria(@Param("productName") String productName,
			@Param("listingStatus") boolean listingStatus, @Param("status") Status status, Pageable pageable);

	public Page<Product> findByListingStatusAndVerified(Pageable pageable, boolean listingStatus, Status status);

	@Query("SELECT p FROM Product p " + "WHERE p.verified = :verifiedStatus " + // Note the space before AND
			"AND p.listingStatus = :listingStatus " + "AND p.subCategory.category.id = :categoryId")
	Page<Product> findFeaturedProductsByCategoryId(@Param("categoryId") String categoryId,
			@Param("listingStatus") Boolean listingStatus, @Param("verifiedStatus") Status verifiedStatus,
			Pageable pageable);

	@Query("SELECT p FROM Product p " + "WHERE p.verified = :verifiedStatus " + // Note the space before AND
			"AND p.listingStatus = :listingStatus " + "AND p.subCategory.id = :subCategoryId")
	Page<Product> findFeaturedProductsBySubCategoryId(@Param("subCategoryId") String subCategoryId,
			@Param("listingStatus") Boolean listingStatus, @Param("verifiedStatus") Status verifiedStatus,
			Pageable pageable);

}
