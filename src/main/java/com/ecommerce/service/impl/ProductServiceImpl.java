package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.PRODUCT;
import static com.ecommerce.util.AppConstant.UNAUTHORIZED;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductImage;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.Status;
import com.ecommerce.model.SubCategory;
import com.ecommerce.model.User;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.payload.ProductResponse;
import com.ecommerce.payload.UpdateStatusBooleanRequest;
import com.ecommerce.payload.UpdateStatusRequest;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.payload.VarientCategoryAttributeResponse;
import com.ecommerce.payload.VarientCategoryJoinResonse;
import com.ecommerce.payload.VarientCategoryReponse;
import com.ecommerce.payload.VarientResponse;
import com.ecommerce.repository.CategoryRepo;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.repository.SubCategoryRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.repository.UserRoleRepo;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;
import com.ecommerce.util.RoleNameIdConstant;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private SubCategoryRepo subCategoryRepo;

	@Autowired
	private AppUtils appUtils;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Map<String, Object> getProductBySubCategory(String subId, Integer page, Integer size, String sortDir) {
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(page, size);
		SubCategory category = subCategoryRepo.findById(subId)
				.orElseThrow(() -> new BadRequestException(AppConstant.SUB_CATEGORY_NOT_FOUND));
		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(page, size, sort1);
		Page<Product> productSet = null;
		productSet = productRepo.findFeaturedProductsBySubCategoryId(subId, Boolean.TRUE, Status.VERIFIED, pageable);		
		Set<ProductResponse> productResponses = productSet.getContent().stream().map(product -> {
			return new ProductResponse().productToProductResponseList(product);
		}).collect(Collectors.toSet());
		PageResponse<ProductResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(productResponses);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(productSet.getNumberOfElements());
		pageResponse.setTotalPages(productSet.getTotalPages());
		pageResponse.setLast(productSet.isLast());
		pageResponse.setFirst(productSet.isFirst());
		response.put("AllProduct", pageResponse);
		return response;
	}
	

	@Override
	public PageResponse<ProductResponse> getProductByVendorId(String vendorId, Integer page, Integer size) {
		User user = userRepo.findById(vendorId).orElseThrow(() -> new BadRequestException(AppConstant.USER_NOT_FOUND));

		boolean flag = user.getUserRole().stream()
				.anyMatch(userRole -> userRole.getRole().getRoleName().equals(RoleName.VENDOR));

		if (!flag) {
			throw new BadRequestException(AppConstant.VENDOR_NOT_FOUND);
		}
		return null;
	}

	@Override
	public Map<String, Object> getAllProduct(String search, Integer pageIndex, Integer pageSize, String sortDir) {
		if (userRoleRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleNameIdConstant.ADMIN))) {
			Map<String, Object> response = new HashMap<>();
			AppUtils.validatePageAndSize(pageIndex, pageSize);
			Sort sort1 = null;
			if (sortDir.equals("DESC")) {
				sort1 = Sort.by(Sort.Order.desc("updatedAt"));
			} else {
				sort1 = Sort.by(Sort.Order.asc("updatedAt"));
			}
			Pageable pageable = PageRequest.of(pageIndex, pageSize);
			Page<Product> productSet = null;
			if (!search.equals("")) {
				System.err.println("Depak");
				productSet = productRepo.findByProductDetail(search, pageable);
			} else {
				productSet = productRepo.findAll(pageable);
			}
			Set<ProductResponse> productResponses = productSet.getContent().stream().map(products -> {
				ProductResponse productResponse = new ProductResponse();
				return productResponse.productToProductResponseList(products);
			}).collect(Collectors.toSet());

			PageResponse<ProductResponse> pageResponse = new PageResponse<>();
			pageResponse.setContent(productResponses);
			pageResponse.setSize(pageSize);
			pageResponse.setPage(pageIndex);
			pageResponse.setTotalElements(productSet.getTotalElements());
			pageResponse.setTotalPages(productSet.getTotalPages());
			pageResponse.setLast(productSet.isLast());
			pageResponse.setFirst(productSet.isFirst());
			response.put("AllProduct", pageResponse);
			return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

//	-------------------------------------------------------------------

//	For updateing the lisiting status of product
	@Override
	public Map<String, Object> updateStatusProduct(UpdateStatusBooleanRequest statusRequest) {
		Map<String, Object> response = new HashMap<>();
		Product product = productRepo.findById(statusRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException(PRODUCT, ID, statusRequest.getId()));
		if (product.getCreatedBy().equals(appUtils.getUserId()) || userRoleRepo
				.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleNameIdConstant.ADMIN))) {

			if (statusRequest.isStatus()) {
				if (!product.getVerified().equals(Status.VERIFIED)) {
					throw new BadRequestException(AppConstant.PRODUCT_NOT_VERIFIED);
				}
				if (Objects.nonNull(product.getVarient()) || product.getVarient().isEmpty()) {
					throw new BadRequestException(AppConstant.NO_ACTIVE_VARIENT);
				}
			}
			product.setListingStatus(statusRequest.isStatus());
			productRepo.save(product);
			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.LISTING_STATUS_UPDATE + statusRequest.isStatus());
			return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

//	get all listing based on verification and listing status product to display customer also based on searching
	@Override
	public Map<String, Object> getProductListBasedOnStatus(String search, Integer pageIndex, Integer pageSize,
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
		Page<Product> productSet = null;
		if (Objects.nonNull(search) && !search.equals("")) {
			System.out.println(search+"non");
			productSet = productRepo.findProductsByNameAndCriteria(search, Boolean.TRUE,
					Status.VERIFIED,pageable);
		} else {
			productSet = productRepo.findByListingStatusAndVerified(pageable, Boolean.TRUE, Status.VERIFIED);
		}
		Set<ProductResponse> productResponses = productSet.getContent().stream().map(products -> {
			ProductResponse productResponse = new ProductResponse();
			return productResponse.productToProductResponseList(products);
		}).collect(Collectors.toSet());
		System.err.println(productResponses);
		PageResponse<ProductResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(productResponses);
		pageResponse.setSize(pageSize);
		pageResponse.setPage(pageIndex);
		pageResponse.setTotalElements(productSet.getTotalElements());
		pageResponse.setTotalPages(productSet.getTotalPages());
		pageResponse.setLast(productSet.isLast());
		pageResponse.setFirst(productSet.isFirst());
		response.put("AllProduct", pageResponse);
		return response;
	}

//	Add product by vendor or admin with non listing and Unverified Status
	@Override
	public Map<String, Object> addProduct(ProductRequest productRequest, MultipartFile multipartFiles) {

		if (productRepo.existsByProductName(productRequest.getProductName())) {
			throw new BadRequestException(AppConstant.PRODUCT_NAME_TAKEN);
		}
		Map<String, Object> response = new HashMap<>();
		Product product = modelMapper.map(productRequest, Product.class);
		product.getDescription().setId(UUID.randomUUID().toString());

		if (multipartFiles != null) {
			String uploadImage = appUtils.uploadImage(multipartFiles, AppConstant.PRODUCT_IMAGE_PATH, null);
			product.setProductImage(uploadImage);
		}
		product.setVerified(Status.UNVERIFIED);
		product.setListingStatus(Boolean.FALSE);
		product.setVendor(new User(appUtils.getUserId()));
		Product product1 = productRepo.save(product);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, AppConstant.PRODUCT_ADDED, HttpStatus.CREATED);
		response.put(AppConstant.RESPONSE_MESSAGE, apiResponse);
		return response;
	}

	// Get product with its Id whose status is true or and edit by as User or User
	// is admin
	// In response we get Product detail and VarientCategory and attribute set
	@Override
	public Map<String, Object> getProduct(String productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(PRODUCT, ID, productId));
		if (product.getListingStatus() || product.getCreatedBy().equals(appUtils.getUserId()) || userRoleRepo
				.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleNameIdConstant.ADMIN))) {
			Map<String, Object> response = new HashMap<>();
			ProductResponse productResponse = new ProductResponse();
			productResponse.productToProductResponse(product);

			Map<String, Set<VarientCategoryAttributeResponse>> varientCat = new HashMap<>();
			productResponse.getVarient().stream().map(varient -> getVarienCatAndAttribute(varient, varientCat))
					.collect(Collectors.toSet());
			response.put("Var", varientCat);
			response.put("Product", productResponse);
			return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

	private Object getVarienCatAndAttribute(VarientResponse varient,
			Map<String, Set<VarientCategoryAttributeResponse>> varientCat) {
		varient.getCategoryJoins().stream().map(catJoin -> setDataToVatCatAttribute(catJoin, varientCat))
				.collect(Collectors.toSet());
		return null;
	}

	private Object setDataToVatCatAttribute(VarientCategoryJoinResonse catJoin,
			Map<String, Set<VarientCategoryAttributeResponse>> varientCat) {
		String key = catJoin.getVarAttribute().getVarientCategory().getName();
		if (varientCat.containsKey(key)) {
			Set<VarientCategoryAttributeResponse> set = varientCat.get(key);
			VarientCategoryAttributeResponse attributeResponse = new VarientCategoryAttributeResponse();
			attributeResponse.setId(catJoin.getVarAttribute().getId());
			attributeResponse.setAttributeName(catJoin.getVarAttribute().getAttributeName());

			if (!set.contains(attributeResponse)) {
				set.add(attributeResponse);
			}
		} else {
			VarientCategoryAttributeResponse attributeResponse = new VarientCategoryAttributeResponse();
			attributeResponse.setId(catJoin.getVarAttribute().getId());
			attributeResponse.setAttributeName(catJoin.getVarAttribute().getAttributeName());
			Set<VarientCategoryAttributeResponse> set = new HashSet<>();
			set.add(attributeResponse);
			varientCat.put(key, set);
		}
		return null;
	}

	@Override
	public Map<String, Object> getProductByCategory(String id, Integer pageIndex, Integer pageSize, String sortDir) {
		Map<String, Object> response = new HashMap<>();
		categoryRepo.findById(id).orElseThrow(() -> new BadRequestException(AppConstant.CATEGORY_NOT_FOUND));
		AppUtils.validatePageAndSize(pageIndex, pageSize);
		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort1);
		Page<Product> productSet = null;
		productSet = productRepo.findFeaturedProductsByCategoryId(id, Boolean.TRUE, Status.VERIFIED, pageable);
		Set<ProductResponse> productResponses = productSet.getContent().stream().map(products -> {
			ProductResponse productResponse = new ProductResponse();
			return productResponse.productToProductResponseList(products);
		}).collect(Collectors.toSet());
		PageResponse<ProductResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(productResponses);
		pageResponse.setSize(pageSize);
		pageResponse.setPage(pageIndex);
		pageResponse.setTotalElements(productSet.getTotalElements());
		pageResponse.setTotalPages(productSet.getTotalPages());
		pageResponse.setLast(productSet.isLast());
		pageResponse.setFirst(productSet.isFirst());
		response.put("AllProduct", pageResponse);
		return response;

	}

}
