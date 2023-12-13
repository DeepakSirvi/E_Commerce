package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Category;
import com.ecommerce.model.MapProductDescription;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductDescription;
import com.ecommerce.model.Status;
import com.ecommerce.model.SubCategory;
import com.ecommerce.model.User;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.MapProductDescriptionRequest;
import com.ecommerce.payload.MapProductDescriptionResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.ProductDescriptionRequest;
import com.ecommerce.payload.ProductDescriptionResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.payload.ProductResponse;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.repository.SubCategoryRepo;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo; 

	@Autowired 
	private SubCategoryRepo categoryRepo;

	
	@Autowired
	private AppUtils appUtils;
	
	@Override
	public Map<String, Object> addProduct(ProductRequest productRequest) {
		
		if(productRepo.existsByProductName(productRequest.getProductName())){
			throw new BadRequestException(AppConstant.PRODUCT_NAME_TAKEN);
			}
		Map<String, Object> response = new HashMap<>();
		Product product = productRequestToProduct(productRequest);

		Product product1 = productRepo.save(product);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, AppConstant.PRODUCT_ADDED);
		response.put("response", apiResponse);
		response.put("product",productToProductResponse(product1));
		return response;
		
		

		
	}

	private Product productRequestToProduct(ProductRequest productRequest) {
		Product product= new Product();
		
		product.setVendor(new User(appUtils.getUserId()));
		product.setProductName(productRequest.getProductName());
		product.setListingStatus(productRequest.getListingStatus());
		product.setBrand(productRequest.getBrand());
		product.setFullfillmentBy(productRequest.getFullfillmentBy());
		product.setShippingProvider(productRequest.getShippingProvider());
		product.setDeliveryCharge(productRequest.getDeliveryCharge());
		product.setProductWeight(productRequest.getProductWeight());
		product.setProductHeight(productRequest.getProductHeight());
		product.setProductWidth(productRequest.getProductWidth());
		product.setProductLength(productRequest.getProductLength());	
		product.setTaxCode(productRequest.getTaxCode());
		product.setCountryOfOrigin(productRequest.getCountryOfOrigin());
		product.setProductType(productRequest.getProductType());
		product.setVerified(Status.UNVERIFIED);
		product.setSubCategory(new SubCategory(productRequest.getSubCategory().getId()));

		ProductDescription productDescription = descriptionRequestToDescription(productRequest.getDescription());
		productDescription.setProduct(product);
		product.setDescription(productDescription);
		
		return product;
	}

	private ProductDescription descriptionRequestToDescription(ProductDescriptionRequest description) {
		ProductDescription productDescription =new ProductDescription();
		productDescription.setDescription(description.getDescription());
	/*	Set<MapProductDescription> mapProductDescription = description.getMapProductDescriptions().stream()
														.map(mapDescription -> mapDescritionRequestToMapDescription(mapDescription)).collect(Collectors.toSet());
		productDescription.setMapProductDescriptions(mapProductDescription);*/
		return productDescription;
	}

//	private MapProductDescription mapDescritionRequestToMapDescription(MapProductDescriptionRequest mapDescription) {
//		MapProductDescription mapProductDescription = new MapProductDescription();
//		mapProductDescription.setTitle(mapDescription.getTitle());
//		mapProductDescription.setDetails(mapDescription.getDetails());
//		return mapProductDescription;
//	}

	@Override
	public PageResponse<ProductResponse> getProductBySubCategory(Long id, Long subId, Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);
		SubCategory category = Optional.of(categoryRepo.findByIdAndCategory(id,new Category(subId))).orElseThrow(()-> new BadRequestException(AppConstant.SUB_CATEGORY_NOT_FOUND));
	    Page<Product> productSet =productRepo.findBySubCategoryAndListingStatus(category,true,pageable);		
		
	    Set<ProductResponse> productResponses = productSet.getContent().stream().map(product -> productToProductResponse(product)
	    		).collect(Collectors.toSet());
		PageResponse<ProductResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(productResponses);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(productSet.getNumberOfElements());
		pageResponse.setTotalPages(productSet.getTotalPages());
		pageResponse.setLast(productSet.isLast());
		pageResponse.setFirst(productSet.isFirst());
		return pageResponse;
	}

	private ProductResponse productToProductResponse(Product product) {
		ProductResponse response = new ProductResponse();
		response.setId(product.getId());
		response.setVendor(new UserResponse(appUtils.getUserId()));
		response.setProductName(product.getProductName());
		response.setListingStatus(product.getListingStatus());
		response.setBrand(product.getBrand());
		response.setFullfillmentBy(product.getFullfillmentBy());
		response.setShippingProvider(product.getShippingProvider());
		response.setDeliveryCharge(product.getDeliveryCharge());
		response.setProductWeight(product.getProductWeight());
		response.setProductHeight(product.getProductHeight());
		response.setProductWidth(product.getProductWidth());
		response.setProductLength(product.getProductLength());	
		response.setTaxCode(product.getTaxCode());
		response.setCountryOfOrigin(product.getCountryOfOrigin());
		response.setProductType(product.getProductType());
		
		ProductDescriptionResponse productDescription = descriptionToDescriptionResponse(product.getDescription());
		
		response.setDescription(productDescription);
		return response;
	}

	private ProductDescriptionResponse descriptionToDescriptionResponse(ProductDescription description) {
		ProductDescriptionResponse productDescription =new ProductDescriptionResponse();
		productDescription.setDescription(description.getDescription());
/*	Set<MapProductDescriptionResponse> mapProductDescription = description.getMapProductDescriptions().stream()
														.map(mapDescription -> mapDescriptionToMapDescriptionResponse(mapDescription)).collect(Collectors.toSet());
		productDescription.setMapProductDescriptions(mapProductDescription);*/
		return productDescription;
	}

//	private MapProductDescriptionResponse mapDescriptionToMapDescriptionResponse(MapProductDescription mapDescription) {
//		MapProductDescriptionResponse mapProductDescription = new MapProductDescriptionResponse();
//		mapProductDescription.setTitle(mapDescription.getTitle());
//		mapProductDescription.setDetails(mapDescription.getDetails());
//		return mapProductDescription;
//	}

	@Override
	public PageResponse<ProductResponse> getAllProduct(Integer page, Integer size,ProductRequest productRequest) {
		AppUtils.validatePageAndSize(page, size);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.EXACT.CONTAINING)
				.withIgnoreCase()
				.withMatcher("id", match->match.transform(value->value.map(id->(((Long)id).intValue()==0)?null:((Long)id).intValue())));
		Example<Product> example = Example.of(productRequestToProduct(productRequest), exampleMatcher);
		
		
		Pageable pageable = PageRequest.of(page, size);	
	    Page<Product> productSet =productRepo.findByListingStatus(true,example,pageable);		
		
	    Set<ProductResponse> productResponses = productSet.getContent().stream().map(product -> productToProductResponseList(product)
	    		).collect(Collectors.toSet());
		PageResponse<ProductResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(productResponses);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(productSet.getNumberOfElements());
		pageResponse.setTotalPages(productSet.getTotalPages());
		pageResponse.setLast(productSet.isLast());
		pageResponse.setFirst(productSet.isFirst());
		return pageResponse;
	}

	private ProductResponse productToProductResponseList(Product product) {
		ProductResponse response = new ProductResponse();
		response.setId(product.getId());
		response.setVendor(new UserResponse(appUtils.getUserId()));
		response.setProductName(product.getProductName());
		response.setListingStatus(product.getListingStatus());
		response.setBrand(product.getBrand());
		response.setCountryOfOrigin(product.getCountryOfOrigin());
		response.setProductType(product.getProductType());
		return response;
		}

	@Override
	public ProductResponse getProduct(Long productId) {
		Product product= productRepo.findById(productId).orElseThrow(()->new BadRequestException(AppConstant.PRODUCT_NOT_FOUND));
		if(!product.getListingStatus()) {
			throw new BadRequestException(AppConstant.PRODUCT_DEACTIVE);
			}
		ProductResponse productResponse = productToProductResponse(product);
		return productResponse;
	}

}
