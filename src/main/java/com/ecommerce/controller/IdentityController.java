package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.IdentityRequest;
import com.ecommerce.service.IdentityService;
import com.ecommerce.util.AppConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ecommerce/identity")
@CrossOrigin
public class IdentityController {

	@Autowired
	private IdentityService identityService;

	@PostMapping("/addIdentity")
	public ResponseEntity<Map<String, Object>> addIdentityDetails(@RequestPart String identityRequest,
			@RequestPart(value = "file", required = false) MultipartFile multipartFile) {

		ObjectMapper mapper = new ObjectMapper();
		IdentityRequest request = null;
		try {
			request = mapper.readValue(identityRequest, IdentityRequest.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> response = identityService.addIdentityDetails(request, multipartFile);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/updateStatus/{identityId}")
	public ResponseEntity<Map<String, Object>> updateStatusById(@PathVariable String identityId) {
		Map<String, Object> response = identityService.updateStatusById(identityId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/ALLIdentity/{userId}")
	public ResponseEntity<Map<String, Object>> getAllIdentitiesById(@PathVariable String userId) {
		Map<String, Object> response = identityService.getAllIdentityById(userId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/identitys/{identityId}")
	public ResponseEntity<Map<String, Object>> getIdentityById(@PathVariable String identityId) {
		Map<String, Object> response = identityService.getIdentityById(identityId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/AllActiveIdentity")
	public ResponseEntity<Map<String, Object>> getAllActiveIdentity(
			@RequestParam(value = "pageIndex", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "pagesize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = "sortDir", required = false, defaultValue = AppConstant.DEFAULT_SORT_DIR) String SortDir) {

		Map<String, Object> response = identityService.getAllActiveIdentity(page, size, SortDir);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/AllIdentity")
	public ResponseEntity<Map<String, Object>> getAllIdentity(
			@RequestParam(value = "pageIndex", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "pagesize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value = "sortDir", required = false, defaultValue = AppConstant.DEFAULT_SORT_DIR) String SortDir) {
		return new ResponseEntity<Map<String, Object>>(identityService.getAllIdentity(page, size, SortDir),
				HttpStatus.OK);
	}
}
