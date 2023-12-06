package com.ecommerce.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Category;
import com.ecommerce.model.MapProductDescription;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductImage;
import com.ecommerce.model.SubCategory;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.model.VarientCategoryJoin;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.payload.ProductResponse;
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
	private ModelMapper mapper;
	
	@Autowired
	private AppUtils appUtils;
	
	@Override
	public ApiResponse addProduct(ProductRequest productRequest) {
		Product product = mapper.map(productRequest, Product.class);
		
		User user = new User();
		user.setId(appUtils.getUserId());
		product.setVendor(user);
		
		product.getDescription().setProduct(product);
		
		for(MapProductDescription productDescription :product.getDescription().getMapProductDescriptions()) {
			productDescription.setProductDescription(product.getDescription());
		}
		
		for(Varient varient: product.getVarient())
		{
			varient.setProduct(product);
			for(ProductImage productImage:varient.getProductImage())
			{
				productImage.setVarientImage(varient);
			}
			
			for(VarientCategoryJoin varientCategoryJoin:varient.getCategoryJoins())
			{
				varientCategoryJoin.setVarient(varient);
			}
		}

        System.out.println(productRequest.getVarient().toString());
		productRepo.save(product);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, AppConstant.PRODUCT_ADDED);
		return apiResponse;
		
		

		
	}

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
		
		return response;
	}

	@Override
	public PageResponse<ProductResponse> getAllProduct(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);	
	    Page<Product> productSet =productRepo.findByListingStatus(true,pageable);		
		
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

	@Override
	public ProductResponse getProduct(Long productId) {
		Product product= productRepo.findById(productId).orElseThrow(()->new BadRequestException(AppConstant.PRODUCT_NOT_FOUND));
		if(!product.getListingStatus()) {
			throw new BadRequestException(AppConstant.PRODUCT_DEACTIVE);
			}
		
		
		ProductResponse productResponse = new ProductResponse();
		productResponse.setId(productId);		
		productResponse.setProductName(product.getProductName());
		productResponse.setListingStatus(product.getListingStatus());
		productResponse.setBrand(product.getBrand());
		productResponse.setFullfillmentBy(product.getFullfillmentBy());
		productResponse.setShippingPovider(product.getShippingProvider());
		productResponse.setDeliveryCharge(product.getDeliveryCharge());
		;
		return productResponse;
	}

}
