package com.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Role;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.RoleRequest;
import com.ecommerce.payload.RoleResponse;
import com.ecommerce.repository.RoleRepo;
import com.ecommerce.service.RoleService;
import com.ecommerce.util.AppConstant;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepo roleRepo;
	
	
	@Override
	public RoleResponse createRole(RoleRequest roleRequest) {
		Role role = new Role();
		role.setRoleName(roleRequest.getRoleName());
		role.setDescription(roleRequest.getDescription());
		if(!roleRepo.existsByRoleName(roleRequest.getRoleName())) {
			role.setCreatedAt(LocalDateTime.now());
			role.setUpdatedAt(LocalDateTime.now());
		Role save = roleRepo.save(role);
		System.out.println(save.getRoleName());
	    RoleResponse roleResponse = new RoleResponse();
	    roleResponse.setId(save.getId());
	    roleResponse.setRoleName(save.getRoleName());
	    roleResponse.setDescription(save.getDescription());
		return roleResponse;
		}
		else
		{
			 ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, AppConstant.ROLE_ALREADY_SAVE);
			   throw new BadRequestException(apiResponse);
		}
	}

}
