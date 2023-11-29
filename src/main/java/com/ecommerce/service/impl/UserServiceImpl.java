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
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.model.UserRole;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.UserRequest;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.service.LoginService;
import com.ecommerce.service.UserService;
import com.ecommerce.util.AppConstant;

@Service
public class UserServiceImpl implements UserService,UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired 
	private ModelMapper mapper;
	
	
	private  ApiResponse apiResponse=null;
	
	public ApiResponse  addUser(UserRequest userRequest)
	{
		if (userRepo.existsByUserMobile(userRequest.getUserMobile())) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.NUMBER_ALREADY_TAKEN);
			throw new BadRequestException(apiResponse);
		}

		if (userRepo.existsByUserEmail(userRequest.getUserEmail())) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.EMAIL_ALREADY_TAKEN);
			throw new BadRequestException(apiResponse);
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
	     apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.INTERNAL_SERVER_ERROR);
	     throw new BadRequestException(apiResponse);
	}

	@Override
	public OtpResponse otpToDeativateAccount(UserRequest userRequest) {
	 return loginService.generateOtp(userRequest.getUserMobile());	
	}

	

}
