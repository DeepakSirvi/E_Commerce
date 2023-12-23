package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.UNAUTHORIZED;
import static com.ecommerce.util.AppConstant.VARIENT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductImage;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.VarientRequest;
import com.ecommerce.payload.VarientResponse;
import com.ecommerce.repository.UserRoleRepo;
import com.ecommerce.repository.VarientRepo;
import com.ecommerce.service.VarientService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

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

	@Override
	public Map<String, Object> createVarient(VarientRequest varientRequest, List<MultipartFile> image) {
		if (varientRepo.existsByVarientName(varientRequest.getVarientName())) {
			throw new BadRequestException(AppConstant.VARIENT_TAKEN);
		}
		Varient varient = modelMapper.map(varientRequest, Varient.class);
		varient.setStatus(Status.DEACTIVE);
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

	@Override
	public Map<String, Object> updateVarientStatus(Long id) {

		Varient varient = varientRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(VARIENT, ID, id));
		Map<String, Object> response = new HashMap<>();
		if (userRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleName.ADMIN))
				|| varient.getCreatedBy().equals(appUtils.getUserId())) {
			varient.setStatus(varient.getStatus() == Status.ACTIVE ? Status.DEACTIVE : Status.ACTIVE);
			varientRepo.save(varient);
			response.put("response", AppConstant.STATUS_UPDATE + id);
			return response;
		}
		throw new UnauthorizedException( UNAUTHORIZED);
	}
	
	@Override
	public Map<String, Object> getVarient(Long id) {
		Varient varient = varientRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(VARIENT, ID, id));
		if (userRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleName.ADMIN))
				|| varient.getCreatedBy().equals(appUtils.getUserId()) || varient.getStatus().equals(Status.ACTIVE)) {
			Map<String, Object> response = new HashMap<>();
			response.put("varient", new VarientResponse().varientToVarientResponse(varient));
			return response;
		}
		throw new UnauthorizedException( UNAUTHORIZED);
	}

	@Override
	public Map<String, Object> updateVarient(VarientRequest varientRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAllVarientByProductId(Long id) {
		return null;
	}


}
