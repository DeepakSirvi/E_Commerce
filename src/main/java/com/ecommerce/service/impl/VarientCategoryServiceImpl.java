package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.UNAUTHORIZED;
import static com.ecommerce.util.AppConstant.VARIENTCATEGORY;
import static com.ecommerce.util.AppConstant.VARIENTCATEGORYATTRIBUTE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.User;
import com.ecommerce.model.VarientCategory;
import com.ecommerce.model.VarientCategoryAttribute;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.CategoryRequest;
import com.ecommerce.payload.CategoryResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.payload.VarientCategoryAttributeRequest;
import com.ecommerce.payload.VarientCategoryAttributeResponse;
import com.ecommerce.payload.VarientCategoryReponse;
import com.ecommerce.payload.VarientCategoryRequest;
import com.ecommerce.repository.UserRoleRepo;
import com.ecommerce.repository.VarientCategoryAttributeRepo;
import com.ecommerce.repository.VarientCategoryRepo;
import com.ecommerce.service.VarientCategoryService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;
import com.ecommerce.util.RoleNameIdConstant;

@Service
public class VarientCategoryServiceImpl implements VarientCategoryService {

	@Autowired
	private VarientCategoryRepo varienCategoryRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;
	@Autowired
	private VarientCategoryAttributeRepo attributeRepo;

	@Autowired
	private AppUtils appUtils;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRoleRepo userRepo;

	@Override
	public Map<String, Object> addVarientCategory(VarientCategoryRequest varientCategory) {

		if (varienCategoryRepo.existsByName(varientCategory.getName())) {
			throw new BadRequestException(AppConstant.VARIENTCAT_TAKEN);
		}

		Map<String, Object> response = new HashMap<>();
		VarientCategory varientCategory2 = modelMapper.map(varientCategory, VarientCategory.class);

		varientCategory2.setUser(new User(appUtils.getUserId()));
		varienCategoryRepo.save(varientCategory2);

		ApiResponse apiResonse = new ApiResponse(Boolean.TRUE, AppConstant.VARIENTCAT_ADDED, HttpStatus.CREATED);
		response.put(AppConstant.RESPONSE_MESSAGE, apiResonse);
		return response;
	}

	@Override
	public Map<String, Object> addVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute) {

		if (attributeRepo.existsByAttributeNameAndVarientCategory(varientCategoryAttribute.getAttributeName(),
				new VarientCategory(varientCategoryAttribute.getVarientCategory().getId()))) {
			throw new BadRequestException(AppConstant.VARIENT_ATTRIBUTE_TAKEN);
		}
		Map<String, Object> response = new HashMap<>();
		VarientCategoryAttribute attribute = modelMapper.map(varientCategoryAttribute, VarientCategoryAttribute.class);
		attributeRepo.save(attribute);
		ApiResponse apiResonse = new ApiResponse(Boolean.TRUE, AppConstant.VARIENT_ATTRIBUTE_ADD, HttpStatus.CREATED);
		response.put(AppConstant.RESPONSE_MESSAGE, apiResonse);
		return response;
	}

	@Override
	public Map<String, Object> deleteVarientCategoryById(Long id) {

		VarientCategory varientCategory = varienCategoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(VARIENTCATEGORY, ID, id));

		if (varientCategory.getCategoryAttributes().size() != 0) {
			throw new BadRequestException(AppConstant.DELETE_ALL_ATTRIBUTE);
		}

		if (userRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleName.ADMIN))
			||	userRoleRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleNameIdConstant.ADMIN))) {
			Map<String, Object> response = new HashMap<>();
			varienCategoryRepo.deleteById(id);
			ApiResponse apiResonse = new ApiResponse(Boolean.TRUE, AppConstant.VARIENTCAT_DELETED, HttpStatus.OK);
			response.put(AppConstant.RESPONSE_MESSAGE, apiResonse);
			return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

	@Override
	public Map<String, Object> deleteVarientCategoryAttributeById(Long id) {

		VarientCategoryAttribute categoryAttribute = attributeRepo.findById(id)
				.orElseThrow(() ->new ResourceNotFoundException(VARIENTCATEGORYATTRIBUTE, ID, id));
		if (categoryAttribute.getCategoryJoins().size() != 0) {
			throw new BadRequestException(AppConstant.DELETE_ALL_PRODUCT);
		}
		if (userRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleName.ADMIN))
				|| userRoleRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleNameIdConstant.ADMIN))) {
		Map<String, Object> response = new HashMap<>();
		attributeRepo.deleteById(id);
		ApiResponse apiResonse = new ApiResponse(Boolean.TRUE, AppConstant.ATTRIBUTE_DELETED, HttpStatus.OK);
		response.put(AppConstant.RESPONSE_MESSAGE, apiResonse);
		return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
		
	}

	@Override
	public Map<String, Object> updateVarientCategory(VarientCategoryRequest varientCategoryRequest) {

		VarientCategory varientCategory = varienCategoryRepo.findById(varientCategoryRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException(VARIENTCATEGORY, ID,varientCategoryRequest.getId()));
		Optional.of(varienCategoryRepo.existsByNameAndIdNot(varientCategoryRequest.getName(), varientCategory.getId()))
				.orElseThrow(() -> new BadRequestException(AppConstant.VARIENTCAT_TAKEN));

		Map<String, Object> response = new HashMap<>();
		varientCategory.setName(varientCategoryRequest.getName());
		varienCategoryRepo.save(varientCategory);
		ApiResponse apiResonse = new ApiResponse(Boolean.TRUE, AppConstant.VARIENTCAT_UPDATED, HttpStatus.OK);
		response.put(AppConstant.RESPONSE_MESSAGE, apiResonse);
		return response;
	}

	@Override
	public Map<String, Object> updateVarientCategoryAttribute(
			VarientCategoryAttributeRequest varientCategoryAttribute) {
		Map<String, Object> response = new HashMap<>();
		VarientCategoryAttribute attribute = attributeRepo.findById(varientCategoryAttribute.getId())
				.orElseThrow(() -> new BadRequestException(AppConstant.VARIENT_ATTIBUTE_NOT_FOUND));

		Optional.of(attributeRepo.existsByAttributeNameAndIdNot(varientCategoryAttribute.getAttributeName(),
				varientCategoryAttribute.getId()))
				.orElseThrow(() -> new BadRequestException(AppConstant.VARIENT_ATTRIBUTE_TAKEN));

		attribute.setAttributeName(varientCategoryAttribute.getAttributeName());
		attributeRepo.save(attribute);
		ApiResponse apiResonse = new ApiResponse(Boolean.TRUE, AppConstant.VARIENT_ATTRIBUTE_UPDATE, HttpStatus.OK);
		response.put(AppConstant.RESPONSE_MESSAGE, apiResonse);
		return response;
	}

	@Override
	public Map<String, Object> getVarientCategoryAttributeById(Long id) {
		VarientCategoryAttribute varientCategoryAttribute = attributeRepo.findById(id)
				.orElseThrow(() -> new BadRequestException(AppConstant.VARIENT_ATTIBUTE_NOT_FOUND));
		Map<String, Object> response = new HashMap<>();
		VarientCategoryAttributeResponse responseAttribute = attributeToAttributResponse(varientCategoryAttribute);
		response.put("varientAttribute", responseAttribute);
		return response;
	}

	@Override
	public Map<String, Object> getVarientCategoryById(Long id) {

		VarientCategory varientCategory = varienCategoryRepo.findById(id)
				.orElseThrow(() -> new BadRequestException(AppConstant.VARIENT_CATEGORY_NOT_FOUND));
		Map<String, Object> response = new HashMap<>();
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

	private VarientCategoryReponse varientCategoryToResponse(VarientCategory varientCategory) {

		VarientCategoryReponse varientCategoryReponse = new VarientCategoryReponse();
		varientCategoryReponse.setId(varientCategory.getId());
		varientCategoryReponse.setName(varientCategory.getName());
		varientCategoryReponse.setUser(new UserResponse(varientCategory.getUser().getId()));

		Set<VarientCategoryAttributeResponse> collect = varientCategory.getCategoryAttributes().stream()
				.map(attribute -> attributeToAttributResponse(attribute)).collect(Collectors.toSet());
		varientCategoryReponse.setCategoryAttributes(collect);
		return varientCategoryReponse;
	}

	@Override
	public Map<String, Object> getAllVarient() {
		List<VarientCategory> findAll = varienCategoryRepo.findAll();
		Map<String, Object> response = new HashMap<>();
		Set<VarientCategoryReponse> varientCategoryReponses = findAll.stream()
				.map(category -> varientCategoryToResponse(category)).collect(Collectors.toSet());
		response.put("AllVarientCategory", varientCategoryReponses);
		return response;
	}

	@Override
	public Map<String, Object> getAllVarientCategory(String search, Integer pageIndex,
			Integer pageSize, String sortDir) {
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(pageIndex, pageSize);
	
		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(pageIndex, pageSize,sort1);
		search=search.trim();
		Page<VarientCategory> findAll=null;
		if(!search.equals("")) {
		 findAll = varienCategoryRepo.getAllVarientList(search,pageable);
		}
		else
		{
		findAll= varienCategoryRepo.findAll(pageable);
		}
		Set<VarientCategoryReponse> varient = findAll.stream().map(varientRes -> varientCategoryToResponse(varientRes))
				.collect(Collectors.toSet());

		PageResponse<VarientCategoryReponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(varient);
		pageResponse.setSize(pageSize);
		pageResponse.setPage(pageIndex);
		pageResponse.setTotalElements(findAll.getTotalElements());
		pageResponse.setTotalPages(findAll.getTotalPages());
		pageResponse.setLast(findAll.isLast());
		pageResponse.setFirst(findAll.isFirst());
		response.put("AllVarientCategory", pageResponse);
		return response;
	}

}
