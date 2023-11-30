package com.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.User;
import com.ecommerce.model.VarientCategory;
import com.ecommerce.model.VarientCategoryAttribute;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.VarientCategoryAttributeRequest;
import com.ecommerce.payload.VarientCategoryRequest;
import com.ecommerce.repository.VarientCategoryAttributeRepo;
import com.ecommerce.repository.VarientCategoryRepo;
import com.ecommerce.service.VarientCategoryService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class VarientCategoryServiceImpl implements VarientCategoryService {

	@Autowired
	private VarientCategoryRepo varienCategoryRepo;
	
	@Autowired
	private VarientCategoryAttributeRepo attributeRepo;	
	
	@Autowired
	private AppUtils appUtils;
	
	@Override
	public ApiResponse addVarientCategory(VarientCategoryRequest varientCategory) {
		if(varienCategoryRepo.existsByName(varientCategory.getName()))
		{
			 ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.CATEGORY_TAKEN);
			   throw new BadRequestException(apiResponse);	
		}
		VarientCategory category = new VarientCategory();
		category.setName(varientCategory.getName());
		
		   User user = new User();
		   user.setId(appUtils.getUserId());
		   category.setUser(user);
		

		
		  for(VarientCategoryAttributeRequest attribute:varientCategory.getCategoryAttributes()) {
			  VarientCategoryAttribute sCategory = new VarientCategoryAttribute();
			  sCategory.setAttributeName(attribute.getAttributeName());
			  sCategory.setVarientCategory(category);
			  category.getCategoryAttributes().add(sCategory);
		  }
		  varienCategoryRepo.save(category);
		  ApiResponse apiResponse = new ApiResponse(Boolean.TRUE,AppConstant.CATEGORY_ADDED);
		  return apiResponse;
	}

	@Override
	public ApiResponse addVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute) {
		VarientCategory varientCategory = new VarientCategory();
		varientCategory.setId(varientCategoryAttribute.getVarientCategory().getId());
		if(attributeRepo.existsByAttributeNameAndVarientCategory(varientCategoryAttribute.getAttributeName(), varientCategory))
		{
			 ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.CATEGORY_TAKEN);
			   throw new BadRequestException(apiResponse);	
		}
		VarientCategoryAttribute attribute  = new VarientCategoryAttribute();
		attribute.setAttributeName(varientCategoryAttribute.getAttributeName());
		attribute.setVarientCategory(varientCategory);
		attributeRepo.save(attribute);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE,AppConstant.CATEGORY_ADDED);
	    return apiResponse;
	}

}
