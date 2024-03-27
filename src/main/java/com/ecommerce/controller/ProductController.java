package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.model.Status;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.payload.ProductResponse;
import com.ecommerce.payload.UpdateStatusBooleanRequest;
import com.ecommerce.payload.UpdateStatusRequest;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.AppConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ecommerce/product")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/admin")
	public ResponseEntity<Map<String, Object>> createProduct(
			@RequestPart(value = "productRequest") String productRequest,
			@RequestPart(value = "file", required = false) MultipartFile multipartFiles) {
		ProductRequest request = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			request = mapper.readValue(productRequest, ProductRequest.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Map<String, Object>>(productService.addProduct(request, multipartFiles),
				HttpStatus.CREATED);
	}

	@PatchMapping("/admin")
	public ResponseEntity<Map<String, Object>> editListingStatusProduct(
			@RequestBody UpdateStatusBooleanRequest statusRequest) {

		return new ResponseEntity<Map<String, Object>>(productService.updateStatusProduct(statusRequest),
				HttpStatus.CREATED);
	}

	@PatchMapping("/admin/status")
	public ResponseEntity<Map<String, Object>> updateProductStatus(@RequestBody UpdateStatusRequest statusRequest) {

		return new ResponseEntity<Map<String, Object>>(productService.updateProductStatus(statusRequest),
				HttpStatus.CREATED);
	}

	@GetMapping("/permitAll/bySubCategory/{subCategoryId}")
	public ResponseEntity<Map<String, Object>> getAllProductBySubCategory(
			@PathVariable(value = "subCategoryId") String subId,
			@RequestParam(value = "pageIndex", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(value = "sortDir", required = false, defaultValue = AppConstant.DEFAULT_SORT_DIR) String sortDir) {

		Map<String, Object> pageResponse = productService.getProductBySubCategory(subId, pageIndex, pageSize, sortDir);
		return new ResponseEntity<Map<String, Object>>(pageResponse, HttpStatus.OK);
	}

	@GetMapping("/permitAll/byCategory/{categoryId}")
	public ResponseEntity<Map<String, Object>> getAllActiveProductByCategory(
			@PathVariable(value = "categoryId") String categoryId,
			@RequestParam(value = "pageIndex", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(value = "sortDir", required = false, defaultValue = AppConstant.DEFAULT_SORT_DIR) String sortDir) {
		Map<String, Object> pageResponse = productService.getProductByCategory(categoryId, pageIndex, pageSize,
				sortDir);
		return new ResponseEntity<>(pageResponse, HttpStatus.OK);
	}

	@GetMapping("/admin/filter")
	public ResponseEntity<Map<String, Object>> getAllProductByCategory(
			@RequestParam(name = "catId", required = false) String catId,
			@RequestParam(name = "date", required = false) String date,
			@RequestParam(name = "status", required = false) Status status,
			@RequestParam(name = "listingStatus", required = false) Boolean listingStatus,
			@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {

		Map<String, Object> pageResponse = productService.getAllProductFilter(catId, date, status, listingStatus,
				pageIndex, pageSize, sortDir);
		return new ResponseEntity<>(pageResponse, HttpStatus.OK);
	}

	@GetMapping("/admin")
	public ResponseEntity<?> getAllProduct(@RequestParam(value = "productSearch", required = false) String search,
			@RequestParam(value = "pageIndex", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(value = "sortDir", required = false, defaultValue = AppConstant.DEFAULT_SORT_DIR) String sortDir) {
		
		return new ResponseEntity<Map<String, Object>>(
				productService.getAllProduct(search, pageIndex, pageSize, sortDir), HttpStatus.OK);
	}

	@GetMapping("/permitAll")
	public ResponseEntity<?> getAllActiveProduct(@RequestParam(value = "productSearch", required = false) String search,
			@RequestParam(value = "pageIndex", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(value = "sortDir", required = false, defaultValue = AppConstant.DEFAULT_SORT_DIR) String sortDir) {
		return new ResponseEntity<Map<String, Object>>(
				productService.getActiveProductList(search, pageIndex, pageSize, sortDir), HttpStatus.OK);
	}

	@GetMapping("/permitAll/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable(value = "productId") String productId) {
		return new ResponseEntity<Map<String, Object>>(productService.getProduct(productId), HttpStatus.OK);
	}

	@GetMapping("/vendor/{vendorId}")
	public ResponseEntity<PageResponse<ProductResponse>> getAllProductOfVender(
			@PathVariable(value = "vendorId") String vendorId,
			@RequestParam(value = "page", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<ProductResponse> pageResponse = productService.getProductByVendorId(vendorId, page, size);
		return new ResponseEntity<>(pageResponse, HttpStatus.OK);
	}

}
