package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.service.annotation.PutExchange;

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
	public Map<String, Object> addVarientCategory(VarientCategoryRequest varientCategory) {
		 Map<String, Object> response = new HashMap<>();
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
		  ApiResponse apiResonse = new ApiResponse(Boolean.TRUE,AppConstant.VARIENTCAT_ADDED);
		  response.put("response", apiResonse);
		  return response;
		  
	}

	@Override
	public Map<String, Object> addVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute) {
		 Map<String, Object> response = new HashMap<>();
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
		 ApiResponse apiResonse = new ApiResponse(Boolean.TRUE,AppConstant.VARIENT_ATTRIBUTE_ADD);
		  response.put("response", apiResonse);
		  return response;
	}



	

	@Override
	public Map<String, Object> deleteVarientCategoryById(Long id) {
		 Map<String, Object> response = new HashMap<>();
		VarientCategory varientCategory = varienCategoryRepo.findById(id).orElseThrow(()->new BadRequestException(AppConstant.VARIENT_CATEGORY_NOT_FOUND));
		if(varientCategory.getCategoryAttributes().size()!=0)
		{
			throw new BadRequestException(AppConstant.DELETE_ALL_ATTRIBUTE);
		}
		varienCategoryRepo.deleteById(id);
		ApiResponse apiResonse = new ApiResponse(Boolean.TRUE,AppConstant.VARIENTCAT_DELETED);
		  response.put("response", apiResonse);
		  return response;
	}
	
	@Override
	public Map<String, Object> deleteVarientCategoryAttributeById(Long id) {	
		 Map<String, Object> response = new HashMap<>();
		VarientCategoryAttribute categoryAttribute = attributeRepo.findById(id).orElseThrow(()->new BadRequestException(AppConstant.VARIENT_ATTRIBUTE_TAKEN));
		if(categoryAttribute.getCategoryJoins().size()!=0)
		{
			throw new BadRequestException(AppConstant.DELETE_ALL_PRODUCT);
		}
		attributeRepo.deleteById(id);
		ApiResponse apiResonse = new ApiResponse(Boolean.TRUE,AppConstant.ATTRIBUTE_DELETED);
		  response.put("response", apiResonse);
		  return response;
	}

	@Override
	public Map<String, Object> updateVarientCategory(VarientCategoryRequest varientCategoryRequest) {
		 Map<String, Object> response = new HashMap<>();
		VarientCategory varientCategory = varienCategoryRepo.findById(varientCategoryRequest.getId()).
				                                     orElseThrow(()->new BadRequestException(AppConstant.VARIENT_CATEGORY_NOT_FOUND));
		Optional.of(varienCategoryRepo.existsByNameAndIdNot(varientCategoryRequest.getName(),varientCategory.getId()))
		                                            .orElseThrow(()->new BadRequestException(AppConstant.VARIENTCAT_TAKEN));
		
	  
		varientCategory.setName(varientCategoryRequest.getName());
		varienCategoryRepo.save(varientCategory);
		ApiResponse apiResonse = new ApiResponse(Boolean.TRUE,AppConstant.VARIENTCAT_UPDATED);
		  response.put("response", apiResonse);
		  return response;
	}
	
	@Override
	public Map<String, Object> updateVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute) {
		 Map<String, Object> response = new HashMap<>();
		VarientCategoryAttribute attribute = attributeRepo.findById(varientCategoryAttribute.getId()).orElseThrow(()->new BadRequestException(AppConstant.VARIENT_ATTIBUTE_NOT_FOUND));
		
		Optional.of(attributeRepo.existsByAttributeNameAndIdNot(varientCategoryAttribute.getAttributeName(),varientCategoryAttribute.getId()))
        .orElseThrow(()->new BadRequestException(AppConstant.VARIENT_ATTRIBUTE_TAKEN));
		
		attribute.setAttributeName(varientCategoryAttribute.getAttributeName());
	    attributeRepo.save(attribute);
	    ApiResponse apiResonse = new ApiResponse(Boolean.TRUE,AppConstant.VARIENT_ATTRIBUTE_UPDATE);
		  response.put("response", apiResonse);
		  return response;
	}

	@Override
	public Map<String, Object> getVarientCategoryAttributeById(Long id) {
		 Map<String, Object> response = new HashMap<>();
		VarientCategoryAttribute varientCategoryAttribute = attributeRepo.findById(id).orElseThrow(()->new BadRequestException(AppConstant.VARIENT_ATTIBUTE_NOT_FOUND));
		
		VarientCategoryAttributeResponse responseAttribute = attributeToAttributResponse(varientCategoryAttribute);
		response.put("varientAttribute", responseAttribute);
		return response;
	}

	@Override
	public Map<String, Object> getVarientCategoryById(Long id) {
		 Map<String, Object> response = new HashMap<>();
		VarientCategory varientCategory = varienCategoryRepo.findById(id).orElseThrow(()->new BadRequestException(AppConstant.VARIENT_CATEGORY_NOT_FOUND));
		
		
		VarientCategoryReponse varientCategoryReponse = varientCategoryToResponse(varientCategory);
		response.put("varientCategory", varientCategoryReponse);
		return response;
	}

	private VarientCategoryAttributeResponse attributeToAttributResponse(VarientCategoryAttribute attribute) {
		VarientCategoryAttributeResponse attributeResponse = new VarientCategoryAttributeResponse();
		attributeResponse.setId(attribute.getId());
		attributeResponse.setAttributeName(attribute.getAttributeName());
		return attributeResponse;
	}

	@Override
	public Map<String, Object> getAllVarient() {
		 Map<String, Object> response = new HashMap<>();
		 List<VarientCategory> findAll = varienCategoryRepo.findAll();
		
		 Set<VarientCategoryReponse> varientCategoryReponses = findAll.stream().map(category -> varientCategoryToResponse(category)).collect(Collectors.toSet());
		 response.put("AllVarientCategory", varientCategoryReponses);
		return response;
	}

	private VarientCategoryReponse varientCategoryToResponse(VarientCategory varientCategory) {
		
		
		VarientCategoryReponse varientCategoryReponse = new  VarientCategoryReponse();
		varientCategoryReponse.setId(varientCategory.getId());
		varientCategoryReponse.setName(varientCategory.getName());
		varientCategoryReponse.setUser(new UserResponse(varientCategory.getUser().getId()));
		
		
		Set<VarientCategoryAttributeResponse> collect = varientCategory.getCategoryAttributes().
				stream().map(attribute -> attributeToAttributResponse(attribute)).collect(Collectors.toSet());
		varientCategoryReponse.setCategoryAttributes(collect);
		return varientCategoryReponse;
	}

}
