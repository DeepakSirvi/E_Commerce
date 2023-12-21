package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Login;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.model.UserRole;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.LoginRequest;
import com.ecommerce.payload.OtpResponse;
import com.ecommerce.payload.RoleResponse;
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

	@Override
	public OtpResponse generateOtp(String phoneNumber) {
		OtpResponse otpResponse = null;
		if (userRepo.existsByUserMobile(phoneNumber)) {
			Optional<User> user = userRepo.findByUserMobile(phoneNumber);
			if (user.get().getStatus().equals(Status.ACTIVE)) {
				Integer otp = appUtils.generateOtp();
				Login login = new Login();
				Optional<Login> oldLogin = loginRepo.findByPhoneNumber(phoneNumber);
				if (oldLogin.isPresent()) {
					login.setId(oldLogin.get().getId());
				}
				login.setPhoneNumber(phoneNumber);
				login.setOtp(otp);
				login.setCreatedAt(LocalDateTime.now());
				login.setExperiedAt(LocalDateTime.now().plusMinutes(15));
				loginRepo.save(login);
				otpResponse = new OtpResponse(otp, AppConstant.OTP_GENERATED);
			} else if (user.get().getStatus().equals(Status.DEACTIVE)) {
				throw new BadRequestException(new ApiResponse(Boolean.FALSE, AppConstant.USER_DEACTIVE));
			} else if (user.get().getStatus().equals(Status.BLOCK)) {
				throw new BadRequestException(new ApiResponse(Boolean.FALSE, AppConstant.USER_BLOCK));
			}
		} else {
			throw new BadRequestException(new ApiResponse(Boolean.FALSE, AppConstant.NEW_USER));
		}
		return otpResponse;
	}

	@Override
	public UserResponse loginUser(LoginRequest loginRequest) {
		if (loginRepo.existsByPhoneNumber(loginRequest.getMobileNumber())) {
			Optional<Login> login = loginRepo.findByPhoneNumberAndOtp(loginRequest.getMobileNumber(),
					loginRequest.getOtp());
			if (login.isPresent()) {

				if (login.get().getExperiedAt().compareTo(LocalDateTime.now()) >= 0) {

					Optional<User> user = userRepo.findByUserMobile(login.get().getPhoneNumber());
					if(user.get().getStatus().equals(Status.ACTIVE))
					{
						UserResponse currentUser = userToUserResponse(user.get());
						currentUser.setToken(jwtUtils.generateToken(login.get().getPhoneNumber(), currentUser.getId()));
						return currentUser;
					}
					else if (user.get().getStatus().equals(Status.DEACTIVE)) {
						throw new BadRequestException(new ApiResponse(Boolean.FALSE, AppConstant.USER_DEACTIVE));
					} 
					else if (user.get().getStatus().equals(Status.BLOCK)) {
						throw new BadRequestException(new ApiResponse(Boolean.FALSE, AppConstant.USER_BLOCK));
					}
				} else {
					throw new BadRequestException(new ApiResponse(Boolean.FALSE,AppConstant.OTP_EXPERED));
				}
			} else {
				throw new BadRequestException(new ApiResponse(Boolean.FALSE,AppConstant.INVALID_OTP));
			}

		} else {
			throw new BadRequestException(new ApiResponse(Boolean.FALSE,AppConstant.INVALID_PHONE_NUMBER));
		}
		return null;

	}

	public UserResponse userToUserResponse(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setFirstName(user.getFirstName());
		userResponse.setLastName(user.getLastName());
		userResponse.setGender(user.getGender());
		userResponse.setId(user.getId());
		userResponse.setUserMobile(user.getUserMobile());
		userResponse.setUserEmail(user.getUserEmail());
		userResponse.setStatus(user.getStatus());

		Set<UserRole> userRoles = user.getUserRole();

		Set<UserRoleResponse> collect = userRoles.stream().map(userRole -> userRoleToUserRoleResponse(userRole))
				.collect(Collectors.toSet());
		userResponse.setUserRole(collect);
		return userResponse;
	}

	public UserRoleResponse userRoleToUserRoleResponse(UserRole userRoles) {
		UserRoleResponse response = new UserRoleResponse();
		response.setId(userRoles.getId());
		response.setRole(new RoleResponse(userRoles.getRole().getId(), userRoles.getRole().getRoleName(),
				userRoles.getRole().getDescription()));
		return response;
	}

}
