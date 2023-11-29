package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Login;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.LoginRequest;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.payload.UserRoleResponse;
import com.ecommerce.repository.LoginRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.service.LoginService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;
import com.ecommerce.util.JwtUtils;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginRepo loginRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private AppUtils appUtils;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public OtpResponse generateOtp(String phoneNumber) {
		OtpResponse otpResponse=null;
       if(userRepo.existsByUserMobile(phoneNumber))
       {
    	   Optional<User> user = userRepo.findByUserMobile(phoneNumber);
    	   if(user.get().getStatus().equals(Status.ACTIVE))
    	   {
	    	   Integer otp = appUtils.generateOtp();
	    	   Login login = new Login();
	    	   Optional<Login> oldLogin = loginRepo.findByPhoneNumber(phoneNumber); 
	    	   if(oldLogin.isPresent()) {
	    		   login.setId(oldLogin.get().getId());
	    	   }
	    	   login.setPhoneNumber(phoneNumber);
	    	   login.setOtp(otp);
	    	   login.setCreatedAt(LocalDateTime.now());
	    	   login.setExperiedAt(LocalDateTime.now().plusMinutes(15));
	    	   loginRepo.save(login);
	    	   otpResponse = new OtpResponse(otp,AppConstant.OTP_GENERATED);
           }
    	   else if(user.get().getStatus().equals(Status.DEACTIVE))
    	   {
    		   ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.USER_DEACTIVE);
    		   throw new BadRequestException(apiResponse);
    	   }
    	   else if(user.get().getStatus().equals(Status.BLOCK))
    	   {
    		   ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.USER_BLOCK);
    		   throw new BadRequestException(apiResponse);
    	   }
       }
       else
       {
    	   ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.NEW_USER);
		   throw new BadRequestException(apiResponse);
       }
		return otpResponse;
	}

	@Override
	public UserResponse loginUser(LoginRequest loginRequest) {
		if(loginRepo.existsByPhoneNumber(loginRequest.getMobileNumber()))
		{
			Optional<Login> login = loginRepo.findByPhoneNumberAndOtp(loginRequest.getMobileNumber(), loginRequest.getOtp());
			if(login.isPresent()) {
				
				if(login.get().getExperiedAt().compareTo(LocalDateTime.now())>=0){
					
					Optional<User> user = userRepo.findByUserMobile(login.get().getPhoneNumber());
					UserResponse currentUser = new UserResponse();
					
					currentUser.setId(user.get().getId());
					currentUser.setUserMobile(user.get().getUserMobile());
					currentUser.setUserEmail(user.get().getUserEmail());
					currentUser.setFirstName(user.get().getFirstName());
					currentUser.setLastName(user.get().getLastName());
					currentUser.setStatus(user.get().getStatus());	
					
					currentUser.setUserRole( user.get().getUserRole().stream()
			                .map(userRole -> modelMapper.map(userRole, UserRoleResponse.class))
			                .collect(Collectors.toSet()));
					
					
					currentUser.setToken(jwtUtils.generateToken(login.get().getPhoneNumber(),currentUser.getId()));	
					return currentUser;
					
				}
				else
				{
					 ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.OTP_EXPERED);
					   throw new BadRequestException(apiResponse);	
				}
				
			}
			else
			{
				 ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.INVALID_OTP);
				   throw new BadRequestException(apiResponse);	
			}
			
		}
		else
		{
			 ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.INVALID_PHONE_NUMBER);
			   throw new BadRequestException(apiResponse);
		}
		
	}

}
