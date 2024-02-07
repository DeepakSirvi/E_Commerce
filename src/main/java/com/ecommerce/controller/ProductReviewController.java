package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.service.ProductReviewService;

@RequestMapping("/ecommerce/productReview")
@RestController
@CrossOrigin("*")
public class ProductReviewController {

	@Autowired
	private ProductReviewService productReviewService;

	@PostMapping("/create")
	public ResponseEntity<?> addProductReview(@RequestParam("numberOfStar") Integer numberOfStar,
			@RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("image") List<MultipartFile> image, @RequestParam("productid") String productid) {

		return productReviewService.addProductReview(numberOfStar, description, productid, image, title);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> DeleteProductReview(@PathVariable String id) {

		return productReviewService.deleteProductReview(id);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProductReview(@PathVariable String id,
			@RequestParam("numberOfStar") Integer numberOfStar, @RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("image") List<MultipartFile> image,
			@RequestParam("productid") String productid) {
		System.out.println("------");
		return productReviewService.updateProductReview(id, numberOfStar, description, productid, image, title);
	}
}
