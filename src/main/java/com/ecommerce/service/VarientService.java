package com.ecommerce.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.UpdateStatusRequest;
import com.ecommerce.payload.VarientRequest;

public interface VarientService {

	public Map<String, Object> createVarient(VarientRequest varientRequest, List<MultipartFile> multipartFiles);

	public Map<String, Object> updateVarient(VarientRequest varientRequest);

	public Map<String, Object> getVarient(String id);

	public Map<String, Object> getAllVarientByProductId(String id);

	public Map<String, Object> updateVarientStatus(UpdateStatusRequest statusRequest);

	public Map<String, Object> getActiveOneVarientByProductId(String id);

	public Map<String, Object> getActiveVarientByCat(List<String> attributeJoinIds, String attributeId, String productId);

}
