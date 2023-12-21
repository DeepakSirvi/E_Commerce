package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.PRODUCT;
import static com.ecommerce.util.AppConstant.UNAUTHORIZED;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.Status;
import com.ecommerce.model.SubCategory;
import com.ecommerce.model.User;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.payload.ProductResponse;
import com.ecommerce.payload.UserResponse;
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
	public Map<String, Object> addProduct(ProductRequest productRequest) {

		if (productRepo.existsByProductName(productRequest.getProductName())) {
			throw new BadRequestException(AppConstant.PRODUCT_NAME_TAKEN);
		}
		Map<String, Object> response = new HashMap<>();
		Product product = modelMapper.map(productRequest, Product.class);
		product.setVerified(Status.UNVERIFIED);
		product.setListingStatus(Boolean.FALSE);
		product.setVendor(new User(appUtils.getUserId()));
		Product product1 = productRepo.save(product);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, AppConstant.PRODUCT_ADDED);
		response.put(AppConstant.RESPONSE_MESSAGE, apiResponse);
		ProductResponse productResponse= new ProductResponse();
		response.put("product", productResponse.productToProductResponse(product1));
		return response;
	}

	@Override
	public PageResponse<ProductResponse> getProductBySubCategory(Long id, Long subId, Integer page, Integer size,
			String sortDir) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);
		SubCategory category = Optional.of(subCategoryRepo.findByIdAndCategory(subId, new Category(id)))
				.orElseThrow(() -> new BadRequestException(AppConstant.SUB_CATEGORY_NOT_FOUND));
		Page<Product> productSet = productRepo.findBySubCategoryAndListingStatus(category, true, pageable);

//		Set<ProductResponse> productResponses = productSet.getContent().stream()
//				.map(product -> productToProductResponse(product)).collect(Collectors.toSet());
		PageResponse<ProductResponse> pageResponse = new PageResponse<>();
//		pageResponse.setContent(productResponses);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(productSet.getNumberOfElements());
		pageResponse.setTotalPages(productSet.getTotalPages());
		pageResponse.setLast(productSet.isLast());
		pageResponse.setFirst(productSet.isFirst());
		return pageResponse;
	}

	@Override
	public PageResponse<ProductResponse> getProductByVendorId(Long vendorId, Integer page, Integer size) {
		User user = userRepo.findById(vendorId).orElseThrow(() -> new BadRequestException(AppConstant.USER_NOT_FOUND));

		boolean flag = user.getUserRole().stream()
				.anyMatch(userRole -> userRole.getRole().getRoleName().equals(RoleName.VENDOR));

		if (!flag) {
			throw new BadRequestException(AppConstant.VENDOR_NOT_FOUND);
		}
		return null;
	}

	@Override
	public Map<String, Object> getProduct(Long productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException(PRODUCT, ID, productId));

		if (product.getListingStatus() || product.getCreatedBy().equals(appUtils.getUserId()) || userRoleRepo
				.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleNameIdConstant.ADMIN))) {
			Map<String, Object> response = new HashMap<>();
			ProductResponse productResponse = new ProductResponse();
			productResponse.setVendor(new UserResponse(appUtils.getUserId()));
			productResponse.productToProductResponse(product);
			response.put("Product", productResponse);
			return response;
		}
		throw new UnauthorizedException(new ApiResponse(Boolean.FALSE, UNAUTHORIZED));
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
			if (!search.equals(" ")) {
				productSet = productRepo.findByProductDetail(search, pageable);
			} else {
				productSet = productRepo.findAll(pageable);
			}
			Set<ProductResponse> productResponses = productSet.getContent().stream()
					.map(products -> {
						ProductResponse productResponse=new ProductResponse();
					 return	productResponse.productToProductResponseList(products);
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
		throw new UnauthorizedException(new ApiResponse(Boolean.FALSE, UNAUTHORIZED));
	}

}
