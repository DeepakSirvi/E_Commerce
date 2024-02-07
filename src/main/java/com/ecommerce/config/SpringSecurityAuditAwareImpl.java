package com.ecommerce.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import com.ecommerce.util.AppUtils;

public class SpringSecurityAuditAwareImpl implements AuditorAware<String> {

	@Autowired
	private AppUtils appUtils;

	@Override
	public Optional<String> getCurrentAuditor() {

		return Optional.ofNullable(appUtils.getUserId());
	}

}
