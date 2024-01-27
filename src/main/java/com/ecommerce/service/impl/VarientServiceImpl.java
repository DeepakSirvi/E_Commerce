package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.PRODUCT;
import static com.ecommerce.util.AppConstant.UNAUTHORIZED;
import static com.ecommerce.util.AppConstant.VARIENT;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductImage;
import com.ecommerce.model.ProductSaveForLater;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.UpdateStatusRequest;
import com.ecommerce.payload.VarientRequest;
import com.ecommerce.payload.VarientResponse;
import com.ecommerce.repository.CartRepo;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.repository.ProductSaveLaterRepo;
import com.ecommerce.repository.UserRoleRepo;
import com.ecommerce.repository.VarientCategoryJoinRepo;
import com.ecommerce.repository.VarientRepo;
import com.ecommerce.repository.WishListRepo;
import com.ecommerce.service.ProductSaveForLaterService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.VarientService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;
import com.ecommerce.util.RoleNameIdConstant;

@Service
public class VarientServiceImpl implements VarientService {

	@Autowired
	private VarientRepo varientRepo;

	@Autowired
	private AppUtils appUtils;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRoleRepo userRepo;

	@Autowired
	private VarientCategoryJoinRepo joinRepo;
	
	@Autowired
	private ProductSaveLaterRepo saveLaterRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private WishListRepo wishListRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	

//	To add varient
	@Override
	public Map<String, Object> createVarient(VarientRequest varientRequest, List<MultipartFile> image) {
		if (varientRepo.existsByVarientName(varientRequest.getVarientName())) {
			throw new BadRequestException(AppConstant.VARIENT_TAKEN);
		}
		Varient varient = modelMapper.map(varientRequest, Varient.class);
		varient.setStatus(Status.DEACTIVE);
		System.out.println(varientRequest.getProductId());
		varient.setProduct(new Product(varientRequest.getProductId()));
		if (image != null) {
			for (MultipartFile file : image) {
				String uploadImage = appUtils.uploadImage(file, AppConstant.PRODUCT_IMAGE_PATH, null);
				ProductImage productImage = new ProductImage();
				productImage.setImageUrl(uploadImage);
				varient.getProductImage().add(productImage);
				productImage.setVarientImage(varient);
			}
		}
		Map<String, Object> response = new HashMap<>();
	Varient save = varientRepo.save(varient);
		response.put(AppConstant.RESPONSE_MESSAGE,
				new ApiResponse(Boolean.TRUE, AppConstant.VARIENT_ADDED, HttpStatus.CREATED));
	  response.put("varient", new VarientResponse().varientToVarientResponse(save));
		return response;
	}

//	 To update status of varient by admin or vendor
	@Override
	public Map<String, Object> updateVarientStatus(UpdateStatusRequest statusRequest) {
		Varient varient = varientRepo.findById(statusRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException(VARIENT, ID, statusRequest.getId()));
		Map<String, Object> response = new HashMap<>();

		if (userRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleNameIdConstant.ADMIN))
				|| varient.getCreatedBy().equals(appUtils.getUserId())) {
			if(statusRequest.getStatus().equals(Status.DEACTIVE))
			{
				saveLaterRepo.deleteByVarient(varient);
				cartRepo.deleteByVarient(varient);
				wishListRepo.deleteByVarient(varient);	 
				varient.getProduct().setListingStatus(Boolean.FALSE);
				productRepo.save(varient.getProduct());
			}
			
			if (varient.getProduct().getVerified().equals(Status.VERIFIED)) {
				varient.setStatus(statusRequest.getStatus());
				varientRepo.save(varient);
				response.put("response", AppConstant.STATUS_UPDATE +" "+ statusRequest.getStatus());
				
			} else {
				response.put("response", "First Product should get verified from admin side");
			}
			return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

// get varient by id if status is active 
	@Override
	public Map<String, Object> getVarient(String id) {
		Varient varient = varientRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(VARIENT, ID, id));
		if (varient.getStatus().equals(Status.ACTIVE)) {
			Map<String, Object> response = new HashMap<>();
			response.put("varient", new VarientResponse().varientToVarientResponse(varient));
			return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

//	Get one active varient of product to display default to user
	@Override
	public Map<String, Object> getActiveOneVarientByProductId(String id) {
		Pageable pageable = PageRequest.of(0, 1);
		List<Varient> varient = varientRepo.findByProductIdAndStatus(id, Status.ACTIVE, pageable);
		if (!varient.isEmpty()) {
			Map<String, Object> response = new HashMap<>();
			response.put("varient", new VarientResponse().varientToVarientResponse(varient.get(0)));
			return response;
		}
		throw new ResourceNotFoundException(AppConstant.PRODUCT, ID, id);
	}

	@Override
	public Map<String, Object> updateVarient(VarientRequest varientRequest) {

		return null;
	}

	@Override
	public Map<String, Object> getAllVarientByProductId(String id) {
		return null;
	}

	@Override
	public Map<String, Object> getActiveVarientByCat(List<String> attributeJoinIds, String attributeId,
			String productId) {
		Map<String, Object> response = new HashMap<>();
		Long varAttributeCount = (long) attributeJoinIds.size();
		List<String> varientIds = new ArrayList<>();
		varientIds = joinRepo.findVarientIdsByVarAttributeIdsAndProductId(attributeJoinIds, productId,
				varAttributeCount);
		if (varientIds.isEmpty()) {
			varientIds = joinRepo.findVarientIdsByVarAttributeIdAndProductId(attributeId, productId);
		}
		response = getVarient(varientIds.get(0));
		return response;

	}
}
