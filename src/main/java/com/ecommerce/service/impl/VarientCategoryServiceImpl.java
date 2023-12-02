package com.ecommerce.service.impl;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.internal.bytebuddy.asm.Advice.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.User;
import com.ecommerce.model.VarientCategory;
import com.ecommerce.model.VarientCategoryAttribute;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.payload.VarientCategoryAttributeRequest;
import com.ecommerce.payload.VarientCategoryAttributeResponse;
import com.ecommerce.payload.VarientCategoryReponse;
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
			   throw new BadRequestException( AppConstant.VARIENT_CATEGORY_NOT_FOUND);	
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
		  ApiResponse apiResponse = new ApiResponse(Boolean.TRUE,AppConstant.VARIENTCAT_ADDED);
		  return apiResponse;
		  
	}

	@Override
	public ApiResponse addVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute) {
		VarientCategory varientCategory = new VarientCategory();
		varientCategory.setId(varientCategoryAttribute.getVarientCategorys().getId());
		if(attributeRepo.existsByAttributeNameAndVarientCategory(varientCategoryAttribute.getAttributeName(), varientCategory))
		{
			   throw new BadRequestException( AppConstant.CATEGORY_TAKEN);	
		}
		VarientCategoryAttribute attribute  = new VarientCategoryAttribute();
		attribute.setAttributeName(varientCategoryAttribute.getAttributeName());
		attribute.setVarientCategory(varientCategory);
		attributeRepo.save(attribute);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE,AppConstant.VARIENT_ATTRIBUTE_ADD);
	    return apiResponse;
	}



	

	@Override
	public ApiResponse deleteVarientCategoryById(Long id) {
		VarientCategory varientCategory = varienCategoryRepo.findById(id).orElseThrow(()->new BadRequestException(AppConstant.VARIENT_CATEGORY_NOT_FOUND));
		if(varientCategory.getCategoryAttributes().size()!=0)
		{
			throw new BadRequestException(AppConstant.DELETE_ALL_ATTRIBUTE);
		}
		varienCategoryRepo.deleteById(id);
		return new ApiResponse(Boolean.TRUE,AppConstant.VARIENTCAT_DELETED);
	}
	
	@Override
	public ApiResponse deleteVarientCategoryAttributeById(Long id) {	
		VarientCategoryAttribute categoryAttribute = attributeRepo.findById(id).orElseThrow(()->new BadRequestException(AppConstant.VARIENT_ATTRIBUTE_TAKEN));
		if(categoryAttribute.getCategoryJoins().size()!=0)
		{
			throw new BadRequestException(AppConstant.DELETE_ALL_PRODUCT);
		}
		attributeRepo.deleteById(id);
		return new ApiResponse(Boolean.TRUE,AppConstant.ATTRIBUTE_DELETED);
	}

	@Override
	public ApiResponse updateVarientCategory(VarientCategoryRequest varientCategoryRequest) {
		VarientCategory varientCategory = varienCategoryRepo.findById(varientCategoryRequest.getId()).
				                                     orElseThrow(()->new BadRequestException(AppConstant.VARIENT_CATEGORY_NOT_FOUND));
		Optional.of(varienCategoryRepo.existsByNameAndIdNot(varientCategoryRequest.getName(),varientCategory.getId()))
		                                            .orElseThrow(()->new BadRequestException(AppConstant.VARIENTCAT_TAKEN));
		
	  
		varientCategory.setName(varientCategoryRequest.getName());
		varienCategoryRepo.save(varientCategory);
		return new ApiResponse(Boolean.TRUE, AppConstant.VARIENTCAT_UPDATED);
	}
	
	@Override
	public ApiResponse updateVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute) {
		VarientCategoryAttribute attribute = attributeRepo.findById(varientCategoryAttribute.getId()).orElseThrow(()->new BadRequestException(AppConstant.VARIENT_ATTIBUTE_NOT_FOUND));
		
		Optional.of(attributeRepo.existsByAttributeNameAndIdNot(varientCategoryAttribute.getAttributeName(),varientCategoryAttribute.getId()))
        .orElseThrow(()->new BadRequestException(AppConstant.VARIENT_ATTRIBUTE_TAKEN));
		
		attribute.setAttributeName(varientCategoryAttribute.getAttributeName());
	    attributeRepo.save(attribute);
		return new ApiResponse(Boolean.TRUE,AppConstant.VARIENT_ATTRIBUTE_UPDATE);
	}

	@Override
	public VarientCategoryAttributeResponse getVarientCategoryAttributeById(Long id) {
		VarientCategoryAttribute varientCategoryAttribute = attributeRepo.findById(id).orElseThrow(()->new BadRequestException(AppConstant.VARIENT_ATTIBUTE_NOT_FOUND));
		
		VarientCategoryAttributeResponse response = new VarientCategoryAttributeResponse();
		response.setId(varientCategoryAttribute.getId());
		response.setAttributeName(varientCategoryAttribute.getAttributeName());
		return response;
	}

	@Override
	public VarientCategoryReponse getVarientCategoryById(Long id) {
		VarientCategory varientCategory = varienCategoryRepo.findById(id).orElseThrow(()->new BadRequestException(AppConstant.VARIENT_CATEGORY_NOT_FOUND));
		
		
		VarientCategoryReponse varientCategoryReponse = new  VarientCategoryReponse();
		varientCategoryReponse.setId(id);
		varientCategoryReponse.setName(varientCategory.getName());
		varientCategoryReponse.setUser(new UserResponse(varientCategory.getUser().getId()));
		
		
		Set<VarientCategoryAttributeResponse> collect = varientCategory.getCategoryAttributes().
				stream().map(attribute -> attributeToAttributResponse(attribute)).collect(Collectors.toSet());
		varientCategoryReponse.setCategoryAttributes(collect);
		
		return varientCategoryReponse;
	}

	private VarientCategoryAttributeResponse attributeToAttributResponse(VarientCategoryAttribute attribute) {
		VarientCategoryAttributeResponse attributeResponse = new VarientCategoryAttributeResponse();
		attributeResponse.setId(attribute.getId());
		attributeResponse.setAttributeName(attribute.getAttributeName());
		return attributeResponse;
	}

}
