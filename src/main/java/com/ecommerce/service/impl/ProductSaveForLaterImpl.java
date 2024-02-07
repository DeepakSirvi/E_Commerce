package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.ProductSaveForLater;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.ProductSaveForLaterRequest;
import com.ecommerce.payload.ProductSaveForLaterResponse;
import com.ecommerce.repository.ProductSaveLaterRepo;
import com.ecommerce.repository.VarientRepo;
import com.ecommerce.service.ProductSaveForLaterService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;


@Service
public class ProductSaveForLaterImpl implements ProductSaveForLaterService {

	@Autowired
	private ProductSaveLaterRepo laterRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AppUtils appUtils;
	
	@Autowired
	private VarientRepo repo;

	public ApiResponse apiresponse;

	public ProductSaveForLater productRequestForLaterTOProductSaveForLater(ProductSaveForLaterRequest forLaterRequest) {
		return this.mapper.map(forLaterRequest, ProductSaveForLater.class);
	}

	public ProductSaveForLaterResponse productSaveForLaterToForLaterResponse(ProductSaveForLater forLater) {
		return this.mapper.map(forLater, ProductSaveForLaterResponse.class);
	}

	@Override
	public Map<String, String> addProductSaveforLater(String vid) {
//		// TODO Auto-generated method stub
		Map<String, String> responce = new HashMap<>();
		Optional<Varient> findById = this.repo.findById(vid);
	    if(laterRepo.existsByUserIdAndVarientId(appUtils.getUserId(),vid)) {
	    	throw new BadRequestException(AppConstant.ALREADY_ADDED);
	    }
	    
		ProductSaveForLater productSaveForLater = new ProductSaveForLater();
		if (findById.isPresent()) {
			Varient varient = new Varient();
			varient.setId(vid);
			User user = new User();
			user.setId(appUtils.getUserId());
			productSaveForLater.setVarient(varient);
			productSaveForLater.setUser(user);
			laterRepo.save(productSaveForLater);
			responce.put(AppConstant.RESPONSE_MESSAGE, AppConstant.PRODUCT_ADDED_SUCCESS);
		} else {
			throw new ResourceNotFoundException(AppConstant.VARIENT, AppConstant.ID, vid);
		}
		return responce;
	}

	@Override
	public Map<String, Object> getAllSaveForLaterByUserId() {
		// TODO Auto-generated method stub
		Map<String , Object> responce =new HashMap<>();
		 List<ProductSaveForLater> forLaters= laterRepo.findByUserId(appUtils.getUserId());
		 //System.out.println(forLaters);
		 List<ProductSaveForLaterResponse> forLaterResponses= forLaters.stream().map(savedLaterProduct -> {
			 ProductSaveForLaterResponse laterResponse=new ProductSaveForLaterResponse();
			 return laterResponse.savedLaterToResponse(savedLaterProduct);
		 }).collect(Collectors.toList());


        responce.put("saveforlate",forLaterResponses);
       
          return responce;
          }

	@Override
	public ResponseEntity<?> deleteSaveForLater(String id) {
		Map<Object , Object> responce =new HashMap<>();

		 Optional<ProductSaveForLater> product = this.laterRepo.findById(id);
		if(product.isPresent()) {
		this.laterRepo.delete(product.get());
		}
		else
		{
			throw new ResourceNotFoundException(AppConstant.PRODUCTSAVEFORLATER_NOT_FOUND, AppConstant.ID, id);
			
		}
		responce.put(AppConstant.MESSAGE, AppConstant.DELETE_SUCCESS);
		return new ResponseEntity<>(responce,HttpStatus.OK);	
	}

}
