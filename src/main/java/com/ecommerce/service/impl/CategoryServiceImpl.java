package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.CATEGORY;
import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.UNAUTHORIZED;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import com.ecommerce.model.Category;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.SubCategory;
import com.ecommerce.model.User;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.CategoryRequest;
import com.ecommerce.payload.CategoryResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.SubCategoryRequest;
import com.ecommerce.payload.SubCategoryResponse;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.repository.CategoryRepo;
import com.ecommerce.repository.SubCategoryRepo;
import com.ecommerce.repository.UserRoleRepo;
import com.ecommerce.service.CategoryService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private SubCategoryRepo subCategoryRepo;

	@Autowired
	private AppUtils appUtils;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Override
	public ApiResponse addSubCategory(SubCategoryRequest subCategoryRequest) {

		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		if (subCategoryRepo.existsBySubCategoryAndCategory(subCategoryRequest.getSubCategory(),
				new Category(subCategoryRequest.getCategory().getId()))) {
			throw new BadRequestException(AppConstant.SUBCATEGORY_TAKEN);
		}
		SubCategory subCategory = modelMapper.map(subCategoryRequest, SubCategory.class);
		subCategoryRepo.save(subCategory);
		return new ApiResponse(Boolean.TRUE, AppConstant.SUBCATEGORY_ADDED, HttpStatus.CREATED);
	}

	private CategoryResponse categoryToCategoryResponse(Category category) {

		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setId(category.getId());
		categoryResponse.setCategoryName(category.getCategoryName());
		List<SubCategory> subCategories = category.getSubCategory();
		List<SubCategoryResponse> collect = subCategories.stream().map(subCat -> {
			return new SubCategoryResponse().subCategoryToSubCategoryResponse(subCat);
		}).collect(Collectors.toList());
		categoryResponse.setSubCategory(collect);
		
		

		UserResponse user = new UserResponse(category.getUser().getId());
		categoryResponse.setUser(user);
		return categoryResponse;
	}

	@Override
	public SubCategoryResponse getSubCategoryById(String id) {
		SubCategory subCategory = subCategoryRepo.findById(id)
				.orElseThrow(() -> new BadRequestException(AppConstant.SUB_CATEGORY_NOT_FOUND));
		SubCategoryResponse response = new SubCategoryResponse();
		response.subCategoryToSubCategoryResponse(subCategory);
		return response;
	}

	@Override
	public ApiResponse updateSubCategory(SubCategoryRequest subCategoryRequest) {
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		
		
		SubCategory subCategory = subCategoryRepo.findById(subCategoryRequest.getId())
				.orElseThrow(() -> new BadRequestException(AppConstant.SUB_CATEGORY_NOT_FOUND));
		
		if (subCategory.getCreatedBy().equals(appUtils.getUserId()) || userRoleRepo
				.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleName.ADMIN.ordinal()))) {
		
		if (subCategoryRepo.existsBySubCategoryAndCategory(subCategoryRequest.getSubCategory(),
				new Category(subCategoryRequest.getCategory().getId()))
				&& !subCategory.getSubCategory().equals(subCategoryRequest.getSubCategory())) {
			throw new BadRequestException(AppConstant.SUBCATEGORY_TAKEN);
		}
		subCategory = modelMapper.map(subCategoryRequest, SubCategory.class);
		subCategoryRepo.save(subCategory);
		return new ApiResponse(Boolean.TRUE, AppConstant.SUBCATEGORY_UPDATED, HttpStatus.OK);
		}
		throw new UnauthorizedException(UNAUTHORIZED);

	}

	@Override
	public ApiResponse addCategory(CategoryRequest categoryRequest) {
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		if (categoryRepo.existsByCategoryName(categoryRequest.getCategoryName())) {
			throw new BadRequestException(AppConstant.CATEGORY_TAKEN);
		}

		Category category = modelMapper.map(categoryRequest, Category.class);
		if (hasDuplicateSubCategoryName(category.getSubCategory())) {
			throw new BadRequestException(AppConstant.DUPLICATE_NOT_ALLOWED);
		}
		category.getSubCategory().stream().map(subCat -> {
			subCat.setCategory(category);
			return null;
		}).collect(Collectors.toList());
		category.setUser(new User(appUtils.getUserId()));
		categoryRepo.save(category);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, AppConstant.CATEGORY_ADDED, HttpStatus.CREATED);
		return apiResponse;
	}

	private boolean hasDuplicateSubCategoryName(List<SubCategory> subCategories) {
		if (subCategories == null || subCategories.isEmpty()) {
			return false; // No duplicates if the list is null or empty
		}

		for (SubCategory subCategory : subCategories) {
			String name = subCategory != null ? subCategory.getSubCategory() : null;
			if (name == null || name.trim().isEmpty()) {
				return false; // If found null, empty, or blank name, return false immediately
			}
		}

		Map<String, Long> subCategoryCounts = subCategories.stream()
				.collect(Collectors.groupingBy(SubCategory::getSubCategory, Collectors.counting()));

		Predicate<Long> isDuplicate = count -> count > 1;

		return subCategoryCounts.values().stream().anyMatch(isDuplicate);
	}

	@Override
	public ApiResponse deleteCategoryById(String id) {
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		Category category = categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, id));
		if (category.getSubCategory().size() != 0)
			throw new BadRequestException(AppConstant.DELETE_SUBCATEGORY);

		if (category.getCreatedBy().equals(appUtils.getUserId()) || userRoleRepo
				.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleName.ADMIN.ordinal()))) {
			categoryRepo.deleteById(id);
			return new ApiResponse(Boolean.TRUE, AppConstant.CATEGORY_DELETED, HttpStatus.OK);
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

	@Override
	public ApiResponse deleteSubCategoryById(String id) {
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		SubCategory subCategory = subCategoryRepo.findById(id)
				.orElseThrow(() -> new BadRequestException(AppConstant.SUB_CATEGORY_NOT_FOUND));

		if (subCategory.getCreatedBy().equals(appUtils.getUserId()) || userRoleRepo
				.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleName.ADMIN.ordinal()))) {
			subCategoryRepo.delete(subCategory);
			return new ApiResponse(Boolean.TRUE, AppConstant.SUBCATEGORY_DELETED, HttpStatus.OK);
		}
		if (subCategory.getProduct().size() != 0)
			throw new BadRequestException(AppConstant.DELETE_PRODUCT);
		throw new UnauthorizedException(UNAUTHORIZED);
	}

	@Override
	public Map<String, Object> getAllCategory() {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Order.asc("categoryName"));
		List<Category> findAll = categoryRepo.findAll(sort);
		List<CategoryResponse> category = findAll.stream().map(categoryRe -> categoryToCategoryResponse(categoryRe))
				.collect(Collectors.toList());
		response.put("AllCategory", category);
		return response;
	}

	@Override
	public Map<String, Object> getCategory(String search, Integer page, Integer size, String sortDirection) {
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(page, size);

		Sort sort1 = null;
		if (sortDirection.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(page, size, sort1);
		Page<Category> findAll = null;
		search = search.trim();
		if (!search.equals("")) {
			System.out.println(search);
			findAll = categoryRepo.getAllCategorySearch(search, pageable);
			System.out.println(findAll);
		} else {
			findAll = categoryRepo.findAll(pageable);
		}
		List<CategoryResponse> category = findAll.stream().map(categoryRe -> categoryToCategoryResponse(categoryRe))
				.collect(Collectors.toList());
		PageResponse<CategoryResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(category);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(findAll.getTotalElements());
		pageResponse.setTotalPages(findAll.getTotalPages());
		pageResponse.setLast(findAll.isLast());
		pageResponse.setFirst(findAll.isFirst());
		response.put("AllCategory", pageResponse);
		return response;
	}

	@Override
	public ApiResponse updateCategory(CategoryRequest categoryRequest) {
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}

		Category category = categoryRepo.findById(categoryRequest.getId())
				.orElseThrow(() -> new BadRequestException(AppConstant.CATEGORY_NOT_FOUND));

		if (categoryRepo.existsByCategoryNameAndIdNot(categoryRequest.getCategoryName(), categoryRequest.getId())) {
			throw new BadRequestException(AppConstant.CATEGORY_TAKEN);
		}
		if (category.getCreatedBy().equals(appUtils.getUserId()) || userRoleRepo
				.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleName.ADMIN.ordinal()))) {
		
		categoryRequest.getSubCategory().stream().map(subCat -> {
			if (Objects.isNull(subCat.getId()) || subCat.getId().equals("")) {
				SubCategoryRequest subCategoryRequest = new SubCategoryRequest();
				subCategoryRequest.setSubCategory(subCat.getSubCategory());
				CategoryRequest categoryReq = new CategoryRequest();
				categoryReq.setId(categoryRequest.getId());
				subCategoryRequest.setCategory(categoryReq);
				addSubCategory(subCategoryRequest);
				return subCategoryRepo.findBySubCategory(subCat.getSubCategory());
			} else {
				subCat.setCategory(new CategoryRequest(categoryRequest.getId()));
				updateSubCategory(subCat);
				return subCat;
			}
		}).collect(Collectors.toList());
		category.setCategoryName(categoryRequest.getCategoryName());
		categoryRepo.save(category);
		return new ApiResponse(Boolean.TRUE, AppConstant.CATEGORY_UPDATED, HttpStatus.OK);
		
		}
		throw new UnauthorizedException(UNAUTHORIZED);

	}

	@Override
	public CategoryResponse getCategoryById(String id) {
		Category category = categoryRepo.findById(id)
				.orElseThrow(() -> new BadRequestException(AppConstant.CATEGORY_NOT_FOUND));
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setId(id);
		categoryResponse.setCategoryName(category.getCategoryName());
		categoryResponse.setUser(new UserResponse(category.getUser().getId()));
		List<SubCategoryResponse> subCategoryResponses = new ArrayList<>();
		category.getSubCategory().stream().forEach(obj -> {
			SubCategoryResponse res = new SubCategoryResponse();
			res.setId(obj.getId());
			res.setSubCategory(obj.getSubCategory());
			subCategoryResponses.add(res);
		});
		categoryResponse.setSubCategory(subCategoryResponses);
		return categoryResponse;
	}
}
