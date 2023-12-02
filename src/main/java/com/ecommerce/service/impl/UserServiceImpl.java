package com.ecommerce.service.impl;



import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Login;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.model.UserRole;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.LoginRequest;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.RoleResponse;
import com.ecommerce.payload.UserRequest;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.payload.UserRoleResponse;
import com.ecommerce.repository.LoginRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.service.LoginService;
import com.ecommerce.service.UserService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class UserServiceImpl implements UserService,UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired 
	private ModelMapper mapper;
	
	@Autowired
	private LoginRepo loginRepo;
	
	@Autowired
	private AppUtils appUtils;
	
	
	private  ApiResponse apiResponse=null;
	
	public ApiResponse  addUser(UserRequest userRequest)
	{
		if (userRepo.existsByUserMobile(userRequest.getUserMobile())) {
			throw new BadRequestException(AppConstant.NUMBER_ALREADY_TAKEN);
		}

		if (userRepo.existsByUserEmail(userRequest.getUserEmail())) {
			throw new BadRequestException(AppConstant.EMAIL_ALREADY_TAKEN);
		}
		
		 Role role = new Role();
		 role.setId(2);
		 role.setRoleName(RoleName.CUSTOMER);
		 
		 UserRole userRole = new UserRole();
		 userRole.setRole(role);
		 User user = mapper.map(userRequest, User.class);
		 userRole.setUser(user);
		 
		 Set<UserRole> userRoles = new HashSet<>();
		 userRoles.add(userRole);
		 
		 
		 user.setUserRole(userRoles);
		 user.setStatus(Status.ACTIVE);
		 user.setCreatedAt(LocalDateTime.now());
		 user.setUpdatedAt(LocalDateTime.now());
		 User user1 = userRepo.save(user);
		
		 if(user1.getId()!=null) {
		  apiResponse = new ApiResponse(Boolean.TRUE, AppConstant.RESGISTRATION_SUCCESSFULLY);
		 }
		 else {
				apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.INTERNAL_SERVER_ERROR);
		 }
		return apiResponse;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> findByUserName = userRepo.findByUserMobile(username);
		 if(findByUserName.isPresent())
		    {
		    	User user=findByUserName.get();
		    	List<GrantedAuthority> authority = user.getUserRole()
		    			.stream()
		    			.map(role->new SimpleGrantedAuthority(role.getRole().getRoleName().name()))
		    			.collect(Collectors.toList());
		    	return new org.springframework.security.core.userdetails.User(username,username,authority);
		    }
	     throw new BadRequestException(AppConstant.INTERNAL_SERVER_ERROR);
	}

	@Override
	public OtpResponse otpToDeativateAccount(UserRequest userRequest) {
	 return loginService.generateOtp(userRequest.getUserMobile());	
	}

	@Override
	public UserResponse getUserById() {
       Optional<User> user = Optional.of(userRepo.findByIdAndStatus(appUtils.getUserId(),Status.ACTIVE).orElseThrow(()->new BadRequestException(AppConstant.USER_NOT_FOUND)));
       
       
       UserResponse userResponse = new UserResponse();
       userResponse.setFirstName(user.get().getFirstName());
       userResponse.setLastName(user.get().getLastName());
       userResponse.setGender(user.get().getGender());
       userResponse.setId(user.get().getId());
       userResponse.setUserMobile(user.get().getUserMobile());
       userResponse.setUserEmail(user.get().getUserEmail());
       userResponse.setStatus(user.get().getStatus());
       
       Set<UserRole> userRoles = user.get().getUserRole();
       
       Set<UserRoleResponse> collect = userRoles.stream().map(userRole -> userRoleToUserRoleResponse(userRole)).collect(Collectors.toSet());
       userResponse.setUserRole(collect);      
       return userResponse;
       
	}

	private UserRoleResponse userRoleToUserRoleResponse(UserRole userRoles) {
       UserRoleResponse response = new UserRoleResponse();
       response.setId(userRoles.getId());
       response.setRole(new RoleResponse(userRoles.getRole().getId(),userRoles.getRole().getRoleName(),userRoles.getRole().getDescription()));
       return response;
	}

	@Override
	public ApiResponse deativateAccount(LoginRequest loginRequest) {
		System.out.println(loginRequest.getMobileNumber());
		if(loginRepo.existsByPhoneNumber(loginRequest.getMobileNumber()))
		{
			Optional<Login> login = loginRepo.findByPhoneNumberAndOtp(loginRequest.getMobileNumber(), loginRequest.getOtp());
			if(login.isPresent()) {
				
				if(login.get().getExperiedAt().compareTo(LocalDateTime.now())>=0){
			    
				// Code to deactivte account
					Optional<User> user = userRepo.findByUserMobile(loginRequest.getMobileNumber());
					user.get().setStatus(Status.DEACTIVE);
					userRepo.save(user.get());
				}
				else
				{
					   throw new BadRequestException( AppConstant.OTP_EXPERED);	
				}
				
			}
			else
			{
				   throw new BadRequestException(AppConstant.INVALID_OTP);	
			}
			
		}
		else
		{
			   throw new BadRequestException(AppConstant.INVALID_PHONE_NUMBER);
		}
		return new ApiResponse(Boolean.TRUE,AppConstant.ACCOUNT_DEACTIVATE); 
	}
}
