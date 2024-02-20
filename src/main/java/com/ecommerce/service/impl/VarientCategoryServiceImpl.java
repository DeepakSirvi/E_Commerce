package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.UNAUTHORIZED;
import static com.ecommerce.util.AppConstant.VARIENTCATEGORY;
import static com.ecommerce.util.AppConstant.VARIENTCATEGORYATTRIBUTE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.User;
import com.ecommerce.model.VarientCategory;
import com.ecommerce.model.VarientCategoryAttribute;
import com.ecommerce.payload.ApiResponse;
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

//	To add varient category like size, Ram, Rom, Color
	@Override
	public Map<String, Object> addVarientCategory(VarientCategoryRequest varientCategory) {

		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		if (varienCategoryRepo.existsByName(varientCategory.getName())) {
			throw new BadRequestException(AppConstant.VARIENTCAT_TAKEN);
		}
		Map<String, Object> response = new HashMap<>();
		VarientCategory varientCategory2 = modelMapper.map(varientCategory, VarientCategory.class);
		if (hasDuplicateAttributeName(varientCategory2.getCategoryAttributes())) {
			throw new BadRequestException(AppConstant.DUPLICATE_NOT_ALLOWED);
		}
		varientCategory2.setUser(new User(appUtils.getUserId()));
		varienCategoryRepo.save(varientCategory2);

		ApiResponse apiResonse = new ApiResponse(Boolean.TRUE, AppConstant.VARIENTCAT_ADDED, HttpStatus.CREATED);
		response.put(AppConstant.RESPONSE_MESSAGE, apiResonse);
		return response;
	}

	private boolean hasDuplicateAttributeName(List<VarientCategoryAttribute> attributes) {
		if (attributes == null || attributes.isEmpty()) {
			return false; // No duplicates if the list is null or empty
		}

		Map<String, Long> attributeCounts = attributes.stream().filter(
				attr -> attr != null && attr.getAttributeName() != null && !attr.getAttributeName().trim().isEmpty())
				.collect(Collectors.groupingBy(VarientCategoryAttribute::getAttributeName, Collectors.counting()));

		Predicate<Long> isDuplicate = count -> count > 1;

		return attributeCounts.values().stream().anyMatch(isDuplicate);
	}

//	To add variant attribute like black, blue, 8GB, 16 GB
	@Override
	public Map<String, Object> addVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute) {

		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
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
	public Map<String, Object> deleteVarientCategoryById(String id) {

		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		VarientCategory varientCategory = varienCategoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(VARIENTCATEGORY, ID, id));

		if (varientCategory.getCategoryAttributes().size() != 0) {
			throw new BadRequestException(AppConstant.DELETE_ALL_ATTRIBUTE);
		}

		if (userRoleRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleName.ADMIN.ordinal()))) {
			Map<String, Object> response = new HashMap<>();
			varienCategoryRepo.deleteById(id);
			ApiResponse apiResonse = new ApiResponse(Boolean.TRUE, AppConstant.VARIENTCAT_DELETED, HttpStatus.OK);
			response.put(AppConstant.RESPONSE_MESSAGE, apiResonse);
			return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

	@Override
	public Map<String, Object> deleteVarientCategoryAttributeById(String id) {

		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		VarientCategoryAttribute categoryAttribute = attributeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(VARIENTCATEGORYATTRIBUTE, ID, id));
		if (categoryAttribute.getCategoryJoins().size() != 0) {
			throw new BadRequestException(AppConstant.DELETE_ALL_PRODUCT);
		}
		if (userRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleName.ADMIN.ordinal()))) {
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

		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		VarientCategory varientCategory = varienCategoryRepo.findById(varientCategoryRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException(VARIENTCATEGORY, ID, varientCategoryRequest.getId()));
		Optional.of(varienCategoryRepo.existsByNameAndIdNot(varientCategoryRequest.getName(), varientCategory.getId()))
				.orElseThrow(() -> new BadRequestException(AppConstant.VARIENTCAT_TAKEN));

		varientCategoryRequest.getCategoryAttributes().stream().map(varAtt -> {
			if (Objects.isNull(varAtt.getId()) || varAtt.getId().equals("")) {
				VarientCategoryAttributeRequest attributeRequest = new VarientCategoryAttributeRequest();
				attributeRequest.setAttributeName(varAtt.getAttributeName());
				VarientCategoryRequest categoryReq = new VarientCategoryRequest();
				categoryReq.setId(varientCategoryRequest.getId());
				attributeRequest.setVarientCategory(categoryReq);
				addVarientCategoryAttribute(attributeRequest);
				return null;
			} else {
				varAtt.setVarientCategory(new VarientCategoryRequest(varientCategoryRequest.getId()));
				updateVarientCategoryAttribute(varAtt);
				return null;
			}
		}).collect(Collectors.toList());
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
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		Map<String, Object> response = new HashMap<>();
		VarientCategoryAttribute attribute = attributeRepo.findById(varientCategoryAttribute.getId())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.VARIENT_ATTIBUTE_NOT_FOUND));

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
	public Map<String, Object> getVarientCategoryAttributeById(String id) {
		VarientCategoryAttribute varientCategoryAttribute = attributeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.VARIENT_ATTIBUTE_NOT_FOUND));
		Map<String, Object> response = new HashMap<>();
		VarientCategoryAttributeResponse responseAttribute = attributeToAttributResponse(varientCategoryAttribute);
		response.put("varientAttribute", responseAttribute);
		return response;
	}

	@Override
	public Map<String, Object> getVarientCategoryById(String id) {

		VarientCategory varientCategory = varienCategoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.VARIENT_CATEGORY_NOT_FOUND));
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

		List<VarientCategoryAttributeResponse> collect = varientCategory.getCategoryAttributes().stream()
				.map(attribute -> attributeToAttributResponse(attribute)).collect(Collectors.toList());
		varientCategoryReponse.setCategoryAttributes(collect);
		return varientCategoryReponse;
	}

//	Get all varient category and its attribute
	@Override
	public Map<String, Object> getAllVarient() {
		List<VarientCategory> findAll = varienCategoryRepo.findAll();
		Map<String, Object> response = new HashMap<>();
		Set<VarientCategoryReponse> varientCategoryReponses = findAll.stream()
				.map(category -> varientCategoryToResponse(category)).collect(Collectors.toSet());
		response.put("AllVarientCategory", varientCategoryReponses);
		return response;
	}

//	Get all varient category and attribute with pagination
	@Override
	public Map<String, Object> getAllVarientCategory(String search, Integer pageIndex, Integer pageSize,
			String sortDir) {
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(pageIndex, pageSize);

		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort1);
		search = search.trim();
		Page<VarientCategory> findAll = null;
		if (!search.equals("")) {
			findAll = varienCategoryRepo.getAllVarientList(search, pageable);
		} else {
			findAll = varienCategoryRepo.findAll(pageable);
		}
		List<VarientCategoryReponse> varient = findAll.stream().map(varientRes -> varientCategoryToResponse(varientRes))
				.collect(Collectors.toList());

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
