package com.ecommerce.prerequriment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecommerce.model.RoleName;
import com.ecommerce.payload.RoleRequest;
import com.ecommerce.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PreRequirment implements CommandLineRunner {

	@Autowired
	private RoleService roleService;

	private static final Logger logger = LoggerFactory.getLogger(PreRequirment.class);

	@Override
	public void run(String... args) throws Exception {

		RoleName roleName[] = RoleName.values();
		for (RoleName name : roleName) {
			RoleRequest role = new RoleRequest();
			role.setId(name.ordinal());
			role.setRoleName(name);
			role.setDescription(name.getDescription());
			try {
				roleService.createRole(role);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
