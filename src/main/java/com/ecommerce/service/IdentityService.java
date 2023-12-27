package com.ecommerce.service;

import java.util.Map;

import com.ecommerce.payload.IdentityRequest;

public interface IdentityService {

	public Map<String, Object> updateIdentityStatus(String id);

	public Map<String, Object> getAllIdentityByUserId(String id);

	public Map<String, Object> getIdentity(String id);

	public Map<String, Object> updateIdentity(IdentityRequest identityRequest);

	public Map<String, Object> createIdentity(IdentityRequest identityRequest);

}
