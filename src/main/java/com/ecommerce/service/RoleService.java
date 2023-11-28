package com.ecommerce.service;

import com.ecommerce.payload.RoleRequest;
import com.ecommerce.payload.RoleResponse;

public interface RoleService {
	
	public RoleResponse createRole(RoleRequest roleRequest);
}
