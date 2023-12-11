package com.ecommerce.service;

import java.util.Map;

import com.ecommerce.payload.IdentityRequest;

public interface IdentityService {

	public Map<String, Object> updateIdentityStatus(Long id);

	public Map<String, Object> getAllIdentityByUserId(Long id);

	public Map<String, Object> getIdentity(Long id);

	public Map<String, Object> updateIdentity(IdentityRequest identityRequest);

	public Map<String, Object> createIdentity(IdentityRequest identityRequest);

}
