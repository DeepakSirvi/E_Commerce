package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Login;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.model.UserRole;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.LoginRequest;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.UpdateUserRequest;
import com.ecommerce.payload.UserRequest;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.payload.UserRoleResponse;
import com.ecommerce.repository.LoginRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.repository.UserRoleRepo;
import com.ecommerce.service.LoginService;
import com.ecommerce.service.UserService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private LoginRepo loginRepo;

	@Autowired
	private AppUtils appUtils;

	private ApiResponse apiResponse = null;

//	To register as customer
	@Override
	public ApiResponse addUser(UserRequest userRequest) {
		if (userRepo.existsByUserMobile(userRequest.getUserMobile())) {
			throw new BadRequestException(AppConstant.NUMBER_ALREADY_TAKEN);
		}

		if (userRepo.existsByUserEmail(userRequest.getUserEmail())) {
			throw new BadRequestException(AppConstant.EMAIL_ALREADY_TAKEN);
		}

		Role role = new Role();
		role.setId(RoleName.CUSTOMER.ordinal());
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		User user = mapper.map(userRequest, User.class);
		userRole.setUser(user);
		List<UserRole> userRoles = new ArrayList<>();
		userRoles.add(userRole);

		user.setUserRole(userRoles);
		user.setStatus(Status.ACTIVE);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		user = userRepo.save(user);
		if (user.getId() != null) {
			apiResponse = new ApiResponse(Boolean.TRUE, AppConstant.RESGISTRATION_SUCCESSFULLY, HttpStatus.CREATED);
		}
		return apiResponse;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		System.err.println(username);
		Optional<User> findByUserName = userRepo.findByUserMobile(username);
		if (findByUserName.isPresent()) {
			User user = findByUserName.get();
			List<GrantedAuthority> authority = user.getUserRole().stream()
					.map(role -> new SimpleGrantedAuthority(role.getRole().getRoleName().name()))
					.collect(Collectors.toList());
			return new org.springframework.security.core.userdetails.User(username, username, authority);
		}
		throw new BadRequestException(AppConstant.NEW_USER);
	}

	@Override
	public OtpResponse otpToDeativateAccount(UserRequest userRequest) {
		return loginService.generateOtp(userRequest.getUserMobile());
	}

	@Override
	public UserResponse getUserById(String userId) {
		User user = userRepo.findById(userId).get();
		if (!Objects.nonNull(user)) {
			throw new ResourceNotFoundException(AppConstant.USER, AppConstant.ID, userId);
		}

		if (!user.getStatus().equals(Status.ACTIVE) && userRoleRepo.existsByUserAndRole(new User(appUtils.getUserId()),
				new Role(RoleName.ADMIN.ordinal()))) {
			throw new ResourceNotFoundException(AppConstant.USER, AppConstant.ID, userId);
		}

		if (user.getStatus().equals(Status.ACTIVE) || userRoleRepo.existsByUserAndRole(new User(appUtils.getUserId()),
				new Role(RoleName.ADMIN.ordinal()))) {
			UserResponse userResponse = new UserResponse();
			userResponse.userToUserResponse(user);
			List<UserRoleResponse> collect = user.getUserRole().stream().map(userRole -> {
				return new UserRoleResponse().userRoleToUserRoleResponse(userRole);
			}).collect(Collectors.toList());
			userResponse.setUserRole(collect);
			return userResponse;
		} else {
			throw new ResourceNotFoundException(AppConstant.USER, AppConstant.ID, userId);
		}
	}

	@Override
	public ApiResponse deativateAccount(LoginRequest loginRequest) {

		if (loginRepo.existsByPhoneNumber(loginRequest.getMobileNumber())) {
			Optional<Login> login = loginRepo.findByPhoneNumberAndOtp(loginRequest.getMobileNumber(),
					loginRequest.getOtp());
			if (login.isPresent()) {
				if (login.get().getExperiedAt().compareTo(LocalDateTime.now()) >= 0) {

					Optional<User> user = userRepo.findByUserMobile(loginRequest.getMobileNumber());
					if (user.get().getStatus().equals(Status.ACTIVE)) {
						user.get().setStatus(Status.DEACTIVE);
						userRepo.save(user.get());
					} else {
						return new ApiResponse(AppConstant.ACCOUNT_DEACTIVATE);
					}
				} else {
					throw new BadRequestException(AppConstant.OTP_EXPERED);

				}
			}

		} else {
			Optional<User> findByUserMobile = userRepo.findByUserMobile(loginRequest.getMobileNumber());
			if (findByUserMobile.isPresent()) {
				throw new BadRequestException("Login first time then deactivate account");
			}
			throw new BadRequestException(AppConstant.INVALID_PHONE_NUMBER);

		}

		return new ApiResponse(AppConstant.ACCOUNT_DEACTIVATE);
	}

	@Override
	public Map<String, Object> updateUser(UpdateUserRequest userRequest) {
		Map<String, Object> response = new HashMap<>();
		Optional<User> userOpt = userRepo.findById(userRequest.getId());
		if (appUtils.isUserActive()) {
			User user = userOpt.get();
			if (userRequest.getFirstName().trim() != "")
				user.setFirstName(userRequest.getFirstName());

			if (userRequest.getLastName().trim() != "")
				user.setLastName(userRequest.getLastName());

			if (userRequest.getGender().trim() != "")
				user.setGender(userRequest.getGender());

			user = userRepo.save(user);
			response.put(AppConstant.MESSAGE, AppConstant.UPDATE_SUCCESSFULLY);
			return response;
		}
		response.put(AppConstant.MESSAGE, AppConstant.UPDATE_FAILED);
		return response;
	}

	@Override
	public Map<String, List<User>> getAllUsersbyGivenRole(String roleTitle) {
		RoleName roleName;
		List<User> allUsers = new ArrayList<>();
		Map<String, List<User>> response = new HashMap<>();
		switch (roleTitle) {
		case "CUSTOMER": {
			roleName = RoleName.CUSTOMER;
			break;
		}
		case "ADMIN": {
			roleName = RoleName.ADMIN;
			break;
		}
		case "VENDOR": {
			roleName = RoleName.VENDOR;
			break;
		}
		case "ALL": {
			allUsers = this.userRepo.findAll();
			response.put("Users", allUsers);
			return response;		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + roleTitle);
		}

		allUsers = this.userRoleRepo.findUsersByRoleName(roleName);
		response.put("Users", allUsers);
		return response;
	}
}
