package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Brand;
import com.ecommerce.model.Identity;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.payload.BrandRequest;
import com.ecommerce.payload.IdentityRequest;
import com.ecommerce.repository.BrandRepo;
import com.ecommerce.repository.IdentityRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.service.IdentityService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class IdentityServiceImpl implements IdentityService {
	
	private static final String IDENTITY = null;

	private static final String ID = null;

	@Autowired
	private IdentityRepo  identityRepo ;
	
	@Autowired
	private AppUtils  appUtils;
	 
	 @Autowired
	 private ModelMapper mapper;
	 
	 @Autowired
		private UserRepo  userRepo;

	@Override
	public Map<String, Object> addIdentityDetails(IdentityRequest identityRequest, MultipartFile multipartFiles) {
		
		Map<String ,Object> response = new HashMap<>();
		
		Identity identity = this.IdentityRequestToIdentity(identityRequest);
		identity.setStatus(Status.DEACTIVE);
		
		
		
		if(identityRepo.findByIdCardNumber(identityRequest.getIdCardNumber()).isPresent()) {
			throw new BadRequestException(AppConstant.IDENTITY_NOT_ADD_SUCCES);
		}
		
		
		 if (multipartFiles != null) {
			 
			 String uploadImage= appUtils.uploadImage(multipartFiles ,AppConstant.Identity_IMAGE_PATH, null);
      	    identity.setImage(uploadImage);  
		 }
			  
		 identity .setUser(new User(appUtils.getUserId()));
		 
		 identityRepo.save(identity);
		 response.put("response",AppConstant.IDENTITY_ADD_SUCCES);
		 
	
		 
		 return response;
	}
	
	private Identity IdentityRequestToIdentity(IdentityRequest identityRequest) {
		
		return this.mapper.map(identityRequest,Identity.class);
	}

	@Override
	public Map<String, Object> updateStatusById(String identityId) {
		
		 Map<String, Object> response = new HashMap<>();
		 
		 Optional<Identity> optionalIdentity =identityRepo .findById(identityId);
		 
		 if (optionalIdentity.isPresent()) {
			 Identity    identity     = optionalIdentity.get();
			 
			 identity .setStatus(Status.ACTIVE);
			 
			 identityRepo.save(identity);
			 
			 response.put("response", AppConstant.UPDATE_STATUS);
			 
			 response.put(AppConstant.STATUS,identity.getStatus().toString());
			 
		 } else {
	           
	       throw new ResourceNotFoundException(IDENTITY, ID, identityId );
	        }
		 return response; 	
	}
		        		
	}


