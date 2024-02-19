package com.ecommerce.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProductReviewService {

	public ResponseEntity<?> addProductReview(Integer numberOfStar, String description, String productid,
			List<MultipartFile> image, String title);

	public ResponseEntity<?> updateProductReview(String id, Integer numberOfStar, String description, String productid,
			List<MultipartFile> image, String title);

	public ResponseEntity<?> deleteProductReview(String id);

}
