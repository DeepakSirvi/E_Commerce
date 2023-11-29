package com.ecommerce.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.util.AppUtils;
import com.ecommerce.util.JwtUtils;

import io.jsonwebtoken.Jwts;

public class SpringSecurityAuditAwareImpl implements AuditorAware<Long> {
	
	@Autowired
	private UserRepo userRepo; 
	
	@Autowired
	private AppUtils appUtils;

	@Override
	public Optional<Long> getCurrentAuditor() {
		
		return Optional.ofNullable(appUtils.getUserId());
	}

}

