package com.ecommerce.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepo;

public class SpringSecurityAuditAwareImpl implements AuditorAware<Long> {
	
	@Autowired
	private UserRepo userRepo; 

	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = 
				SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}
		System.out.println(authentication);	
		User userPrincipal = userRepo.findByUserMobile(authentication.getPrincipal().toString()).get();
		return Optional.ofNullable(userPrincipal.getId());
	}

}

