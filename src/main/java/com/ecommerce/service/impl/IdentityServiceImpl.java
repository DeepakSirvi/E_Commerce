package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Identity;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.payload.IdentityRequest;
import com.ecommerce.payload.IdentityResponse;
import com.ecommerce.repository.IdentityRepo;
import com.ecommerce.service.IdentityService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class IdentityServiceImpl implements IdentityService {

	private static final String IDENTITY = null;

	private static final String ID = null;

	@Autowired
	private IdentityRepo identityRepo;

	@Autowired
	private AppUtils appUtils;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Map<String, Object> addIdentityDetails(IdentityRequest identityRequest, MultipartFile multipartFiles) {

		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		Map<String, Object> response = new HashMap<>();
		Identity identity = this.IdentityRequestToIdentity(identityRequest);
		identity.setStatus(Status.DEACTIVE);
		if (identityRepo.findByIdCardNumber(identityRequest.getIdCardNumber()).isPresent()) {
			throw new BadRequestException(AppConstant.IDENTITY_NOT_ADD_SUCCES);
		}
		if (multipartFiles != null) {
			String uploadImage = appUtils.uploadImage(multipartFiles, AppConstant.Identity_IMAGE_PATH, null);
			identity.setImage(uploadImage);
		}
		identity.setUser(new User(appUtils.getUserId()));
		identityRepo.save(identity);
		response.put("response", AppConstant.IDENTITY_ADD_SUCCES);
		return response;
	}

	private Identity IdentityRequestToIdentity(IdentityRequest identityRequest) {

		return this.mapper.map(identityRequest, Identity.class);
	}

	@Override
	public Map<String, Object> updateStatusById(String identityId) {
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		Map<String, Object> response = new HashMap<>();
		Optional<Identity> optionalIdentity = identityRepo.findById(identityId);
		if (optionalIdentity.isPresent()) {
			Identity identity = optionalIdentity.get();
			identity.setStatus(Status.ACTIVE);
			if (optionalIdentity.isPresent()) {
				Identity identity1 = optionalIdentity.get();
				identity1.setStatus(Status.DEACTIVE);
			}
			identityRepo.save(identity);
			response.put("response", AppConstant.UPDATE_STATUS);
			response.put(AppConstant.STATUS, identity.getStatus().toString());

		} else {

			throw new ResourceNotFoundException(IDENTITY, ID, identityId);
		}
		return response;
	}

	@Override
	public Map<String, Object> getAllIdentityById(String userId) {
		Map<String, Object> response = new HashMap<>();
		List<Identity> identity = identityRepo.findAllByUserId(userId);
		List<IdentityResponse> identityResponses = identity.stream().map(obj -> identityResponse(obj))
				.collect(Collectors.toList());
		response.put("identity", identityResponses);
		return response;
	}

	public IdentityResponse identityResponse(Identity identity) {

		IdentityResponse identityResponse = new IdentityResponse();
		identityResponse.setId(identity.getId());
		identityResponse.setIdCardName(identity.getIdCardName());
		identityResponse.setIdCardNumber(identity.getIdCardNumber());
		identityResponse.setImage(identity.getImage());
		identityResponse.setDescription(identity.getDescription());
		return identityResponse;
	}

	@Override
	public Map<String, Object> getIdentityById(String identityId) {

		Map<String, Object> response = new HashMap<>();

		Optional<Identity> identityOptional = identityRepo.findById(identityId);

		if (identityOptional.isPresent()) {

			Identity identity = identityOptional.get();

			IdentityResponse identityResponse = identityToIdentityResponse(identity);

			response.put("identity", identityResponse);

		} else {
			throw new ResourceNotFoundException(IDENTITY, ID, identityId);
		}

		return response;
	}

	private IdentityResponse identityToIdentityResponse(Identity identity2) {
		IdentityResponse identityResponse = new IdentityResponse();
		identityResponse.setId(identity2.getId());
		identityResponse.setIdCardName(identity2.getIdCardName());
		identityResponse.setIdCardNumber(identity2.getIdCardNumber());
		identityResponse.setDescription(identity2.getDescription());
		identityResponse.setImage(identity2.getImage());

		return identityResponse;
	}

	@Override
	public Map<String, Object> getAllActiveIdentity(Integer page, Integer size, String sortDir) {
		Map<String, Object> response = new HashMap<>();

		AppUtils.validatePageAndSize(page, size);
		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}

		Pageable pageable = PageRequest.of(page, size, sort1);

		Page<Identity> findAllActiveIdentity = identityRepo.findAllActiveIdentity(pageable);

		response.put("response", findAllActiveIdentity.getContent().stream().map(obj -> identityFilter(obj))
				.collect(Collectors.toList()));

		response.put(AppConstant.MESSAGE, AppConstant.ALL_ACTIVE_IDENTITY);
		return response;
	}

	private Object identityFilter(Identity obj) {

		IdentityResponse identityResponse = new IdentityResponse();
		identityResponse.setId(obj.getId());
		identityResponse.setIdCardName(obj.getIdCardName());
		identityResponse.setIdCardNumber(obj.getIdCardNumber());
		identityResponse.setDescription(obj.getDescription());
		identityResponse.setImage(obj.getImage());
		return identityResponse;
	}

	@Override
	public Map<String, Object> getAllIdentity(Integer page, Integer size, String sortDir) {

		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(page, size);

		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}

		Pageable pageable = PageRequest.of(page, size, sort1);

		Page<Identity> identitySet = null;
		identitySet = identityRepo.findAll(pageable);
		if (!identitySet.isEmpty()) {
			List<IdentityResponse> identityResponse = identitySet.stream().map(this::identityToIdentityResponse)
					.collect(Collectors.toList());
			response.put("AllIdentity", identityResponse);
		} else {
			throw new ResourceNotFoundException();
		}
		return response;
	}

}
