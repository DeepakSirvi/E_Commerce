package com.ecommerce.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.VarientRequest;

public interface VarientService {

	public Map<String, Object> createVarient(VarientRequest varientRequest, MultipartFile[] image);

	public Map<String, Object> updateVarient(VarientRequest varientRequest);

	public Map<String, Object> getVarient(Long id);

	public Map<String, Object> getAllVarientByProductId(Long id);

	public Map<String, Object> updateVarientStatus(Long id);

}
