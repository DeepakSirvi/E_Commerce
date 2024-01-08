package com.ecommerce.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Role;
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
		if(!roleRepo.existsByRoleName(roleRequest.getRoleName())) {
			Role role = new Role();
			role.setId(roleRequest.getId());
			role.setRoleName(roleRequest.getRoleName());
			role.setDescription(roleRequest.getDescription());
			role.setCreatedAt(LocalDateTime.now());
			role.setUpdatedAt(LocalDateTime.now());
		    role = roleRepo.save(role);
			System.out.println(role.getRoleName());
			
		    RoleResponse roleResponse = new RoleResponse();
		    roleResponse.setId(role.getId());
		    roleResponse.setRoleName(role.getRoleName());
		    roleResponse.setDescription(role.getDescription());
			return roleResponse;
		}
		else
		{
			   throw new BadRequestException(AppConstant.ROLE_ALREADY_SAVE);
		}
	}

}
