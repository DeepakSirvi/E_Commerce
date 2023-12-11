package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductImage;
import com.ecommerce.model.Varient;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.VarientRequest;
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
	public Map<String, Object> createVarient(VarientRequest varientRequest,MultipartFile[] image) {
        if(varientRepo.existsByVarientName(varientRequest.getVarientName()))
        {
        	throw new BadRequestException(AppConstant.VARIENT_TAKEN);
        }
		Varient varient= varientRequestToVarient(varientRequest,image);	
		Map<String,Object> response = new HashMap<>();
		Varient save = varientRepo.save(varient);
		
		response.put("response", new ApiResponse(Boolean.TRUE, AppConstant.VARIENT_ADDED));
		response.put("varient", save);
		return response;
	}

	private Varient varientRequestToVarient(VarientRequest varientRequest,MultipartFile[] image) {
		Varient varient = new Varient();
		varient.setVarientName(varientRequest.getVarientName());
		varient.setPrice(varientRequest.getPrice());
		varient.setStock(varientRequest.getStock());
		varient.setStatus(varientRequest.getStatus());
//		varient.setProduct(new Product(varientRequest.getProduct().getId()));
		
		for(MultipartFile file:image)
		{
			String uploadImage = appUtils.uploadImage(file, AppConstant.PRODUCT_IMAGE_PATH);
			ProductImage productImage= new ProductImage();
			productImage.setImageUrl(uploadImage);
			varient.getProductImage().add(productImage);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAllVarientByProductId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateVarientStatus(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
