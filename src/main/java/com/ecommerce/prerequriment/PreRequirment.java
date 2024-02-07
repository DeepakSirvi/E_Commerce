package com.ecommerce.prerequriment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecommerce.model.RoleName;
import com.ecommerce.payload.RoleRequest;
import com.ecommerce.service.RoleService;
import com.ecommerce.util.RoleNameIdConstant;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PreRequirment implements CommandLineRunner {

	@Autowired
	private RoleService roleService;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		RoleRequest role = new RoleRequest();
		role.setId(RoleNameIdConstant.ADMIN);
		role.setRoleName(RoleName.ADMIN);
		role.setDescription("This role is for admin who have all authority");
		try {
			roleService.createRole(role);
		} catch (Exception e) {
			System.out.println(e);
		}
		role.setRoleName(RoleName.CUSTOMER);
		role.setId(RoleNameIdConstant.CUSTOMER);
		role.setDescription("This role is for customer who is here for shopping");
		try {
			roleService.createRole(role);
		} catch (Exception e) {
			System.out.println(e);
		}

		role.setRoleName(RoleName.VENDOR);
		role.setId(RoleNameIdConstant.VENDOR);
		role.setDescription("This role is for vender who is here to sell his product");
		try {
			roleService.createRole(role);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
