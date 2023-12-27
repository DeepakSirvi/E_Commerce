package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
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

	@Override
	public ApiResponse addCategory(CategoryRequest categoryRequest) {
		System.out.println(categoryRequest.getId());
		if (categoryRepo.existsByCategoryName(categoryRequest.getCategoryName())) {
			throw new BadRequestException(AppConstant.CATEGORY_TAKEN);
		}
		Category category = modelMapper.map(categoryRequest, Category.class);
		category.setUser(new User(appUtils.getUserId()));
		categoryRepo.save(category);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, AppConstant.CATEGORY_ADDED, HttpStatus.CREATED);
		return apiResponse;
	}

	@Override
	public ApiResponse addSubCategory(SubCategoryRequest subCategoryRequest) {

		if (subCategoryRepo.existsBySubCategoryAndCategory(subCategoryRequest.getSubCategory(),
				new Category(subCategoryRequest.getCategory().getId()))) {
			throw new BadRequestException(AppConstant.SUBCATEGORY_TAKEN);
		}
		SubCategory subCategory = modelMapper.map(subCategoryRequest, SubCategory.class);
		subCategoryRepo.save(subCategory);

		return new ApiResponse(Boolean.TRUE, AppConstant.SUBCATEGORY_ADDED, HttpStatus.CREATED);
	}

	@Override
	public CategoryResponse getCategoryById(Long id) {
		Category category = categoryRepo.findById(id)
				.orElseThrow(() -> new BadRequestException(AppConstant.CATEGORY_NOT_FOUND));
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setId(id);
		categoryResponse.setCategoryName(category.getCategoryName());
		categoryResponse.setUser(new UserResponse(category.getUser().getId()));
		return categoryResponse;
	}

	private CategoryResponse categoryToCategoryResponse(Category category) {

		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setId(category.getId());
		categoryResponse.setCategoryName(category.getCategoryName());

		Set<SubCategory> subCategories = category.getSubCategory();

		Set<SubCategoryResponse> collect = subCategories.stream()
				.map(subCat -> this.subCategoryToSubCategoryResponse(subCat)).collect(Collectors.toSet());
		categoryResponse.setSubCategory(collect);

		UserResponse user = new UserResponse(category.getUser().getId());
		categoryResponse.setUser(user);
		return categoryResponse;
	}

	private SubCategoryResponse subCategoryToSubCategoryResponse(SubCategory s) {
		SubCategoryResponse response = new SubCategoryResponse();
		response.setId(s.getId());
		response.setSubCategory(s.getSubCategory());
		return response;
	}

	@Override
	public SubCategoryResponse getSubCategoryById(Long id) {
		SubCategory subCategory = subCategoryRepo.findById(id)
				.orElseThrow(() -> new BadRequestException(AppConstant.SUB_CATEGORY_NOT_FOUND));
		SubCategoryResponse response = subCategoryToSubCategoryResponse(subCategory);
		return response;
	}

	@Override
	public ApiResponse deleteCategoryById(Long id) {
		Category category = categoryRepo.findById(id)
				.orElseThrow(() -> new BadRequestException(AppConstant.CATEGORY_NOT_FOUND));

		if (category.getSubCategory().size() != 0)
			throw new BadRequestException(AppConstant.DELETE_SUBCATEGORY);
		categoryRepo.deleteById(id);
		return new ApiResponse(Boolean.TRUE, AppConstant.CATEGORY_DELETED, HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteSubCategoryById(Long id) {
		SubCategory subCategory = subCategoryRepo.findById(id)
				.orElseThrow(() -> new BadRequestException(AppConstant.SUB_CATEGORY_NOT_FOUND));
		if (subCategory.getProduct().size() != 0)
			throw new BadRequestException(AppConstant.DELETE_PRODUCT);
		subCategoryRepo.deleteById(id);
		return new ApiResponse(Boolean.TRUE, AppConstant.SUBCATEGORY_DELETED, HttpStatus.OK);
	}

	@Override
	public ApiResponse updateCategory(CategoryRequest categoryRequest) {
		Category category = categoryRepo.findById(categoryRequest.getId())
				.orElseThrow(() -> new BadRequestException(AppConstant.CATEGORY_NOT_FOUND));

		if (categoryRepo.existsByCategoryNameAndIdNot(categoryRequest.getCategoryName(), categoryRequest.getId())) {
			throw new BadRequestException(AppConstant.CATEGORY_TAKEN);
		}

		category.setCategoryName(categoryRequest.getCategoryName());
		categoryRepo.save(category);
		return new ApiResponse(Boolean.TRUE, AppConstant.CATEGORY_UPDATED, HttpStatus.OK);
	}

	@Override
	public ApiResponse updateSubCategory(SubCategoryRequest subCategoryRequest) {
		SubCategory subCategory = subCategoryRepo.findById(subCategoryRequest.getId())
				.orElseThrow(() -> new BadRequestException(AppConstant.SUB_CATEGORY_NOT_FOUND));
		if (subCategoryRepo.existsBySubCategoryAndCategory(subCategoryRequest.getSubCategory(),
				new Category(subCategoryRequest.getCategory().getId()))) {
			throw new BadRequestException(AppConstant.SUBCATEGORY_TAKEN);
		}
		subCategory = modelMapper.map(subCategoryRequest, SubCategory.class);
		subCategoryRepo.save(subCategory);
		return new ApiResponse(Boolean.TRUE, AppConstant.SUBCATEGORY_UPDATED, HttpStatus.OK);
	}

	@Override
	public Map<String, Object> getCategory(String search,Integer page, Integer size, String sortDirection) {
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(page, size);
		
		Sort sort1 = null;
		if (sortDirection.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(page, size,sort1);
		Page<Category> findAll=null;
		 search = search.trim();
		if(!search.equals("")) {
			System.out.println(search);
		 findAll = categoryRepo.getAllCategorySearch(search,pageable);
		}
		else
		{
		findAll= categoryRepo.findAll(pageable);
		}
		Set<CategoryResponse> category = findAll.stream().map(categoryRe -> categoryToCategoryResponse(categoryRe))
		 .collect(Collectors.toSet());
		System.out.println(category);
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
	public Map<String, Object> getAllCategory() {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Order.asc("categoryName"));
		List<Category> findAll = categoryRepo.findAll(sort);	
		Set<CategoryResponse> category = findAll.stream().map(categoryRe -> categoryToCategoryResponse(categoryRe))
				.collect(Collectors.toSet());
		response.put("AllCategory", category);
		return response;
	}

}
