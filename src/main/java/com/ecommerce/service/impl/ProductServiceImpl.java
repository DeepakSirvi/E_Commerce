package com.ecommerce.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.MapProductDescription;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductDescription;
import com.ecommerce.model.ProductImage;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.model.VarientCategoryAttribute;
import com.ecommerce.model.VarientCategoryJoin;
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
import com.ecommerce.util.AppUtils;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo; 

	
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

}
