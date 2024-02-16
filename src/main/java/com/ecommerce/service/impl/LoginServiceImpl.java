package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Override
	public OtpResponse generateOtp(String phoneNumber) {
		OtpResponse otpResponse = null;
		String regexPattern = "^\\d{10}$";

		if (!phoneNumber.matches(regexPattern)) {
			throw new BadRequestException(AppConstant.INVALID_PHONE_NUMBER);
		}

		if (userRepo.existsByUserMobile(phoneNumber)) {
			Optional<User> user = userRepo.findByUserMobile(phoneNumber);
			if (user.get().getStatus().equals(Status.ACTIVE) || user.get().getStatus().equals(Status.DEACTIVE)) {
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
			} else if (user.get().getStatus().equals(Status.BLOCK)) {
				throw new BadRequestException(AppConstant.USER_BLOCK);
			}
		} else {
			throw new BadRequestException(AppConstant.NEW_USER);
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

					User user = userRepo.findByUserMobile(login.get().getPhoneNumber()).get();
					if (user.getStatus().equals(Status.ACTIVE) || user.getStatus().equals(Status.DEACTIVE)) {

						if (user.getStatus().equals(Status.DEACTIVE)) {
							user.setStatus(Status.ACTIVE);
							userRepo.save(user);
						}
						UserResponse currentUser = new UserResponse();
						currentUser.userToUserResponse(user);
						List<UserRoleResponse> collect = user.getUserRole().stream().map(userRole -> {
							return new UserRoleResponse().userRoleToUserRoleResponse(userRole);
						}).collect(Collectors.toList());
						currentUser.setUserRole(collect);
						currentUser.setToken(jwtUtils.generateToken(login.get().getPhoneNumber(), currentUser.getId()));
						return currentUser;
					} else if (user.getStatus().equals(Status.BLOCK)) {
						throw new BadRequestException(AppConstant.USER_BLOCK);
					}
				} else {
					throw new BadRequestException(AppConstant.OTP_EXPERED);
				}
			} else {
				throw new BadRequestException(AppConstant.INVALID_OTP);
			}

		} else {
			throw new BadRequestException(AppConstant.INVALID_PHONE_NUMBER);
		}
		return null;
	}

	@Override
	public ApiResponse logoutUser() {
		jwtUtils.logout();
		return new ApiResponse(AppConstant.LOGOUT_SUCCESSFULL);
	}

}
