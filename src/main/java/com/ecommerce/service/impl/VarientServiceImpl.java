package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public Map<String, Object> createVarient(VarientRequest varientRequest,List<MultipartFile> image) {
        if(varientRepo.existsByVarientName(varientRequest.getVarientName()))
        {
        	throw new BadRequestException(AppConstant.VARIENT_TAKEN);
        }
		Varient varient= modelMapper.map(varientRequest,Varient.class);
        varient.setStatus(Status.DEACTIVE);
        varient.setProduct(new Product(varientRequest.getProductId()));
           	
		for(MultipartFile file:image)
		{
			String uploadImage = appUtils.uploadImage(file, AppConstant.PRODUCT_IMAGE_PATH,null);
			ProductImage productImage= new ProductImage();
			productImage.setImageUrl(uploadImage);
			varient.getProductImage().add(productImage);
			productImage.setVarientImage(varient);	
		}
		
		Map<String,Object> response = new HashMap<>();
		Varient save = varientRepo.save(varient);
		response.put(AppConstant.RESPONSE_MESSAGE, new ApiResponse(Boolean.TRUE, AppConstant.VARIENT_ADDED,HttpStatus.CREATED));
		response.put("varient", varientToVarientResponse(save));
		return response;
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
	
	private VarientResponse varientToVarientResponse(Varient varient) {
		VarientResponse varientResponse= new VarientResponse();
		varientResponse.setId(varient.getId());		
		return varientResponse;
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
