package com.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Category;
import com.ecommerce.model.SubCategory;
import com.ecommerce.model.User;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.CategoryRequest;
import com.ecommerce.payload.SubCategoryRequest;
import com.ecommerce.repository.CategoryRepo;
import com.ecommerce.service.CategoryService;
import com.ecommerce.util.AppConstant;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public ApiResponse addCategory(CategoryRequest categoryRequest) {
		if(categoryRepo.existsByCategoryName(categoryRequest.getCategoryName()))
		{
			 ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.CATEGORY_TAKEN);
			   throw new BadRequestException(apiResponse);	
		}
		Category category = new Category();
		category.setCategoryName(categoryRequest.getCategoryName());
		
		   User user = new User();
		   user.setId(categoryRequest.getUser().getUserId());
		   category.setUser(user);
		

		
		  for(SubCategoryRequest subCategory:categoryRequest.getSubCategory()) {
			  SubCategory sCategory = new SubCategory();
			  sCategory.setSubCategory(subCategory.getSubCategory());
			
			  sCategory.setCategory(category);
			  category.getSubCategory().add(sCategory);
		  }
			categoryRepo.save(category);
			ApiResponse apiResponse = new ApiResponse(Boolean.TRUE,AppConstant.CATEGORY_ADDED);
		    return apiResponse;
	}

}
