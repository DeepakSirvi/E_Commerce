package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.VarientCategoryJoin;

public interface VarientCategoryJoinRepo extends JpaRepository<VarientCategoryJoin, String> {

	@Query("SELECT vcj.varient.id " + "FROM VarientCategoryJoin vcj " + "WHERE vcj.varAttribute.id IN :varAttributeIds "
			+ "AND vcj.varient.product.id = :productId " + "GROUP BY vcj.varient.id "
			+ "HAVING COUNT(DISTINCT vcj.varAttribute.id) = :varAttributeCount")
	List<String> findVarientIdsByVarAttributeIdsAndProductId(@Param("varAttributeIds") List<String> varAttributeIds,
			@Param("productId") String productId, @Param("varAttributeCount") Long varAttributeCount);

	@Query("SELECT vcj.varient.id " + "FROM VarientCategoryJoin vcj " + "WHERE vcj.varAttribute.id = :varAttributeId "
			+ "AND vcj.varient.product.id = :productId")
	List<String> findVarientIdsByVarAttributeIdAndProductId(@Param("varAttributeId") String varAttributeId,
			@Param("productId") String productId);
}
