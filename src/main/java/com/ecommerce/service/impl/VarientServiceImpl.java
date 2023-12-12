package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductImage;
import com.ecommerce.model.Status;
import com.ecommerce.model.Varient;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.VarientRequest;
import com.ecommerce.payload.VarientResponse;
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
	
	
	@Override
	public Map<String, Object> createVarient(VarientRequest varientRequest,List<MultipartFile> image) {
        if(varientRepo.existsByVarientName(varientRequest.getVarientName()))
        {
        	throw new BadRequestException(AppConstant.VARIENT_TAKEN);
        }
		Varient varient= varientRequestToVarient(varientRequest,image);	
		Map<String,Object> response = new HashMap<>();
		Varient save = varientRepo.save(varient);
		
		response.put("response", new ApiResponse(Boolean.TRUE, AppConstant.VARIENT_ADDED));
		response.put("varient", varientToVarientResponse(save));
		return response;
	}

	private VarientResponse varientToVarientResponse(Varient varient) {
		VarientResponse varientResponse= new VarientResponse();
		varientResponse.setId(varient.getId());		
		
		return varientResponse;
	}

	private Varient varientRequestToVarient(VarientRequest varientRequest,List<MultipartFile> image) {
		Varient varient = new Varient();
		varient.setVarientName(varientRequest.getVarientName());
		varient.setPrice(varientRequest.getPrice());
		varient.setStock(varientRequest.getStock());
		varient.setStatus(varientRequest.getStatus());
		varient.setProduct(new Product(varientRequest.getProduct().getId()));
		
		varient.setStatus(Status.DEACTIVE);
		
		for(MultipartFile file:image)
		{
			String uploadImage = appUtils.uploadImage(file, AppConstant.PRODUCT_IMAGE_PATH,null);
			ProductImage productImage= new ProductImage();
			productImage.setImageUrl(uploadImage);
			varient.getProductImage().add(productImage);
			productImage.setVarientImage(varient);	
			}
			
		return varient;
	}

	@Override
	public Map<String, Object> updateVarient(VarientRequest varientRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getVarient(Long id) {
	
	 Varient varient= varientRepo.findById(id).
			  orElseThrow(()->new BadRequestException(AppConstant.VARIENT_NOT_FOUND + id));		
		Map<String,Object> response = new HashMap<>();
		 VarientResponse varientResponse =  varientToVarientResponse(varient);
		 response.put("varient", varientResponse);
		 return response;
	}

	@Override
	public Map<String, Object> getAllVarientByProductId(Long id) {
	   
		return null;
	}

	@Override
	public Map<String, Object> updateVarientStatus(Long id) {
		 Varient varient= varientRepo.findById(id).
				  orElseThrow(()->new BadRequestException(AppConstant.VARIENT_NOT_FOUND + id));		
			Map<String,Object> response = new HashMap<>();
			varient.setStatus(varient.getStatus() == Status.ACTIVE ? Status.DEACTIVE : Status.ACTIVE);
			varientRepo.save(varient);
			response.put("response",AppConstant.STATUS_UPDATE + id);
			 return response;
	}

}
