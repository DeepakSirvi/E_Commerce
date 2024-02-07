package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.PRODUCT;
import static com.ecommerce.util.AppConstant.UNAUTHORIZED;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
import com.ecommerce.model.Product;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.payload.ProductResponse;
import com.ecommerce.payload.UpdateStatusBooleanRequest;
import com.ecommerce.payload.UpdateStatusRequest;
import com.ecommerce.payload.VarientCategoryAttributeResponse;
import com.ecommerce.payload.VarientCategoryJoinResonse;
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
			Pageable pageable = PageRequest.of(pageIndex, pageSize, sort1);
			Page<Product> productSet = null;
			if (!search.equals("")) {
				productSet = productRepo.findByProductDetail(search, pageable);
			} else {
				productSet = productRepo.findAll(pageable);
			}
			List<ProductResponse> productResponses = productSet.getContent().stream().map(products -> {
				ProductResponse productResponse = new ProductResponse();
				return productResponse.productToProductResponseList(products);
			}).collect(Collectors.toList());

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
				if ((Objects.nonNull(product.getVarient()) || !product.getVarient().isEmpty())) {
					Boolean flag = product.getVarient().stream()
							.anyMatch(varient -> varient.getStatus().equals(Status.ACTIVE));
					if (!flag) {
						throw new BadRequestException(AppConstant.NO_ACTIVE_VARIENT);
					}
				}
				product.setListingStatus(statusRequest.isStatus());
				productRepo.save(product);

			} else {
				product.setListingStatus(statusRequest.isStatus());
				product.setVarient(product.getVarient().stream().map(varient -> {
					varient.setStatus(Status.DEACTIVE);
					return varient;
				}).collect(Collectors.toList()));

				productRepo.save(product);
			}
			response.put(AppConstant.RESPONSE_MESSAGE,
					AppConstant.LISTING_STATUS_UPDATE + " " + statusRequest.isStatus());
			return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

	@Override
	public Map<String, Object> getActiveProductList(String search, Integer pageIndex, Integer pageSize,
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
			productSet = productRepo.findProductsByNameAndCriteria(search, Boolean.TRUE, Status.VERIFIED, pageable);
		} else {
			productSet = productRepo.findByListingStatusAndVerified(pageable, Boolean.TRUE, Status.VERIFIED);
		}
		List<ProductResponse> productResponses = productSet.getContent().stream().map(products -> {
			ProductResponse productResponse = new ProductResponse();
			return productResponse.productToProductResponseList(products);
		}).collect(Collectors.toList());

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
		productRepo.save(product);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, AppConstant.PRODUCT_ADDED, HttpStatus.CREATED);
		response.put(AppConstant.RESPONSE_MESSAGE, apiResponse);

		return response;
	}

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
		List<ProductResponse> productResponses = productSet.getContent().stream().map(products -> {
			ProductResponse productResponse = new ProductResponse();
			return productResponse.productToProductResponseList(products);
		}).collect(Collectors.toList());
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

	@Override
	public Map<String, Object> getProductBySubCategory(String subId, Integer page, Integer size, String sortDir) {
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(page, size);
		subCategoryRepo.findById(subId).orElseThrow(() -> new BadRequestException(AppConstant.SUB_CATEGORY_NOT_FOUND));
		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(page, size, sort1);
		Page<Product> productSet = null;
		productSet = productRepo.findFeaturedProductsBySubCategoryId(subId, Boolean.TRUE, Status.VERIFIED, pageable);
		List<ProductResponse> productResponses = productSet.getContent().stream().map(product -> {
			return new ProductResponse().productToProductResponseList(product);
		}).collect(Collectors.toList());
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
	public Map<String, Object> getAllProductFilter(String catId, String date, Status status, Boolean listingStatus,
			int pageIndex, int pageSize, String sortDir) {
		System.err.println(date);
		System.err.println(catId);
		System.err.println(status);
		System.err.println(listingStatus);
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(pageIndex, pageSize);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase()
				.withMatcher("id", match -> match.transform(value -> value.map(id -> ((Long) id == 0) ? null : id)));
		Product request = new Product();
		request.setListingStatus(listingStatus);
		request.setVerified(status);
		if (!date.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		    request.setCreatedAt(LocalDate.parse(date , formatter));
//		    request.setUpdatedAt(LocalDate.parse(date , formatter));
		}
		request.getSubCategory().getCategory().setId(catId);
		Example<Product> example = Example.of(request, exampleMatcher);

		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(pageIndex, pageSize, sort1);
		Page<Product> productSet = productRepo.findAll(example, pageable);
//		if (listingStatus != null)
//			productSet = productRepo.findProductByFilter(catId, date, status, listingStatus, pageable);
//		else {
//			productSet = productRepo.findProductByFilterWithOutListing(catId, date, status, pageable);
//
//		}
		List<ProductResponse> productResponses = productSet.getContent().stream().map(product -> {
			return new ProductResponse().productToProductResponseList(product);
		}).collect(Collectors.toList());

		PageResponse<ProductResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(productResponses);
		pageResponse.setSize(productSet.getSize());
		pageResponse.setPage(productSet.getNumberOfElements());
		pageResponse.setTotalElements(productSet.getNumberOfElements());
		pageResponse.setTotalPages(productSet.getTotalPages());
		pageResponse.setLast(productSet.isLast());
		pageResponse.setFirst(productSet.isFirst());
		response.put("AllProduct", pageResponse);
		System.err.println(productResponses);
		return response;
	}

	@Override
	public Map<String, Object> updateProductStatus(UpdateStatusRequest statusRequest) {
		Map<String, Object> response = new HashMap<>();
		Product product = productRepo.findById(statusRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException(PRODUCT, ID, statusRequest.getId()));

		if (statusRequest.getStatus().equals(product.getVerified())) {
			throw new BadRequestException(AppConstant.INVALID_TRANSITION);
		} else if (statusRequest.getStatus().equals(Status.VERIFIED)) {
			product.setVerified(statusRequest.getStatus());
			product.setListingStatus(Boolean.FALSE);
			productRepo.save(product);
		} else {
			product.setVerified(statusRequest.getStatus());
			product.setListingStatus(Boolean.FALSE);
			product.setVarient(product.getVarient().stream().map(varient -> {
				varient.setStatus(Status.DEACTIVE);
				return varient;
			}).collect(Collectors.toList()));

			productRepo.save(product);
		}
		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.STATUS_UPDATE + " " + statusRequest.getStatus());
		return response;
	}

}
