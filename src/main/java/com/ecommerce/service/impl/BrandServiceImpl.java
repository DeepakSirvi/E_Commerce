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

import com.ecommerce.FileService.CloudService;
import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Brand;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.payload.BrandRequest;
import com.ecommerce.payload.BrandResponse;
import com.ecommerce.repository.BrandRepo;
import com.ecommerce.service.BrandService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class BrandServiceImpl implements BrandService {

	private static final String ID = null;
	private static final String BRAND = null;
	@Autowired
	private BrandRepo brandRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private AppUtils appUtils;
	@Autowired
	private CloudService cloudService;
	
	public Brand  brandRequestToBrand1(BrandRequest brandRequest) {
		Brand brand = new Brand();
		brand.setBrandDescription(brandRequest.getBrandDescription());
		brand.setBrandImage(brandRequest.getBrandImage());
		brand.setBrandName(brandRequest.getBrandName());
		brand.setStatus(Status.UNVERIFIED);
		return brand;
	}

	
	
	public  BrandResponse brandToBrandResponse1(Brand brand) {
		BrandResponse brandResponse = new BrandResponse();
		brandResponse.setBrandDescription(brand.getBrandDescription());
		brandResponse.setBrandImage(brand.getBrandImage());
		brandResponse.setBrandName(brand.getBrandName());
		brandResponse.setStatus(Status.UNVERIFIED);
		return brandResponse ;
	}
	
	

	@Override
	public Map<String, Object> addBrandDetails(BrandRequest brandRequest, MultipartFile multipartFiles) {
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		Map<String, Object> response = new HashMap<>();
		System.err.println("---");
		Brand brand = this.brandRequestToBrand1(brandRequest);
	brand.setStatus(Status.UNVERIFIED);
		if (multipartFiles != null) {
			String profileName = cloudService.uploadFileInFolder(multipartFiles, AppConstant.BRAND_IMAGE_PATH);

//			String uploadImage = appUtils.uploadImage(multipartFiles, AppConstant.BRAND_IMAGE_PATH, null);
//			System.err.println(multipartFiles);

			brand.setBrandImage(profileName);

		}

		brand.setUser(new User(appUtils.getUserId()));

		brandRepo.save(brand);
		response.put("response", AppConstant.BRAND_ADD_SUCCES);
		return response;
	}

	

	@Override
	public Map<String, Object> updateStatusById(String brandId) {
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		
		Map<String, Object> response = new HashMap<>();
		Optional<Brand> optionalBrand = brandRepo.findById(brandId);
		if (optionalBrand.isPresent()) {
			Brand brand = optionalBrand.get();
			brand.setStatus(Status.UNVERIFIED);
			if (optionalBrand.isPresent()) {
				Brand brand1 = optionalBrand.get();
				brand1.setStatus(Status.ACTIVE);
			}
			brandRepo.save(brand);
			response.put("response", AppConstant.UPDATE_STATUS);
			response.put(AppConstant.STATUS, brand.getStatus().toString());
		} else {
			throw new ResourceNotFoundException(BRAND, ID, brandId);
		}
		return response;
	}

	@Override
	public Map<String, Object> getBrandById(String brandId) {

		Map<String, Object> response = new HashMap<>();
		Optional<Brand> brandOptional = brandRepo.findById(brandId);

		if (brandOptional.isPresent()) {
			Brand brand = brandOptional.get();

			BrandResponse brandResponse = brandToBrandResponse1(brand);
			response.put("brand", brandResponse);

		} else {

			throw new ResourceNotFoundException(BRAND, ID, brandId);

		}
		return response;
	}

	private BrandResponse brandToBrandResponse(Brand brand2) {
		BrandResponse brandResponse = new BrandResponse();
		brandResponse.setId(brand2.getId());
		
		brandResponse.setBrandName(brand2.getBrandName());
		brandResponse.setBrandDescription(brand2.getBrandDescription());
		brandResponse.setBrandImage(brand2.getBrandImage());
		return brandResponse;
	}

	@Override
	public Map<String, Object> getAllBrandById(String userId) {

		Map<String, Object> response = new HashMap<>();

		List<Brand> brands = brandRepo.findAllByUserId(userId);

		List<BrandResponse> brandResponse = brands.stream().map(this::brandToBrandResponse1)
				.collect(Collectors.toList());

		response.put("brand", brandResponse);
		return response;

	}

	@Override
	public Map<String, List<BrandResponse>> getAllBrand(Integer page, Integer size, String sortDir) {

		Map<String, List<BrandResponse>> response = new HashMap<>();
		AppUtils.validatePageAndSize(page, size);

		 
		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}

		// Sort.by(Sort.Direction.DESC,"updatedAt")
		Pageable pageable = PageRequest.of(page, size, sort1);

		Page<Brand> brandSet = brandRepo.findByStatusNot(pageable,"Deactived");
		if (!brandSet.isEmpty()) {
			List<BrandResponse> brandResponses = brandSet.getContent().stream().map(d->this.brandToBrandResponse(d)).collect(Collectors.toList());
			response.put("AllBrand", brandResponses);
			System.err.println();
		} else {
			throw new ResourceNotFoundException();
		}
		return response;
	}

	@Override
	public Map<String, Object> getVerfiedBrandById(String brandId) {

		Map<String, Object> response = new HashMap<>();

		Optional<Brand> optionalBrand = brandRepo.findById(brandId);

		if (!optionalBrand.isPresent()) {
			throw new ResourceNotFoundException("Brand Not Found");

		}
		if (optionalBrand.get().getStatus().toString().equals("VERIFIED")) {
			response.put("response", AppConstant.BRAND_VERIFIED);
			response.put("data", optionalBrand.get());
		} else {
			response.put("message", AppConstant.BRAND_NOT_VERIFIED);
		}

		return response;
	}

	@Override
	public Map<String, Object> getAllVerfiedBrand(Integer pageIndex, Integer pagesize, String sortDir) {
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(pageIndex, pagesize);
		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(pageIndex, pagesize, sort1);
		Page<Brand> findAllVerfiedBrand = brandRepo.findAllVerfiedBrand(pageable);
		response.put("response",
				findAllVerfiedBrand.getContent().stream().map(obj -> brandFilter(obj)).collect(Collectors.toList()));

		response.put(AppConstant.MESSAGE, AppConstant.ALL_VERFIED_BRAND);

		return response;
	}

	public BrandResponse brandFilter(Brand obj) {
		BrandResponse brandResponse = new BrandResponse();
		brandResponse.setId(obj.getId());
		brandResponse.setBrandDescription(obj.getBrandDescription());
		brandResponse.setBrandImage(obj.getBrandImage());
		brandResponse.setBrandName(obj.getBrandName());
		return brandResponse;

	}

	

	@Override
	public boolean deleteBrand(String id) {
		
		Optional<Brand> findById = this.brandRepo.findById(id);
		if(findById.isPresent()) {
			Brand brand =  findById.get();
			brand.setStatus(Status.DEACTIVE);
			this.brandRepo.save(brand);
		}
		else {
			throw new  ResourceNotFoundException();
		}
		return false;
		
	}
	
	@Override
	public Map<String, Object> updateAddress(BrandRequest brandRequest, MultipartFile multipartFiles) {
	
		System.err.println(brandRequest.getId());

		Map<String, Object> responce = new HashMap<>();
		
		Optional<Brand> findById = this.brandRepo.findById(brandRequest.getId());
		
		if(findById.isEmpty()) {
			 throw new BadRequestException(AppConstant.BRAND_UPDATE);
			
		}
		else
		{
		
			Brand brand = findById.get();
			brand.setId(brandRequest.getId());
			brand.setBrandDescription(brandRequest.getBrandDescription());
			brand.setBrandImage(brandRequest.getBrandImage());
			brand.setStatus(Status.UNVERIFIED);
			brand.setBrandName(brandRequest.getBrandName());
			
			 
				if (multipartFiles != null) {
					String profileName = cloudService.uploadFileInFolder(multipartFiles, AppConstant.BRAND_IMAGE_PATH);


					brand.setBrandImage(profileName);

				}
				this.brandRepo.save(brand);
			 
			 responce.put(AppConstant.RESPONSE_MESSAGE,AppConstant.UPDATE_SUCCESSFULLY );
		}
		
		return responce;
	}

		
	}




