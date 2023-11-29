package com.ecommerce.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Product;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.repository.CategoryRepo;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.repository.SubCategoryRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.repository.VarientCategoryAttributeRepo;
import com.ecommerce.repository.VarientRepo;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.AppConstant;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo; 

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private SubCategoryRepo subCategoryRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private VarientRepo varientRepo;
	
	@Autowired
	private VarientCategoryAttributeRepo categoryAttributeRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public ApiResponse addProduct(ProductRequest productRequest) {
		Product product = mapper.map(productRequest, Product.class);
		productRepo.save(product);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, AppConstant.PRODUCT_ADDED);
		   return apiResponse;
		
		

		
	}

}
