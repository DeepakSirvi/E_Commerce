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

public class SpringSecurityAuditAwareImpl implements AuditorAware<String> {
	
	@Autowired
	private AppUtils appUtils;

	@Override
	public Optional<String> getCurrentAuditor() {
		
		return Optional.ofNullable(appUtils.getUserId());
	}

}

