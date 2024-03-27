package com.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Address;
import com.ecommerce.model.User;
import com.ecommerce.payload.AddressRequest;
import com.ecommerce.payload.AddressResponse;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.repository.AddressRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.service.AddressService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private AppUtils appUtils;
	@Autowired
	private UserRepo userRepo;

	public ApiResponse apiresponse;

	public AddressResponse addressToAddressResponse(Address address) {

		AddressResponse as = new AddressResponse();
		as.setAddressType(address.getAddressType());	
		as.setCity(address.getCity());
		as.setLandMark(address.getLandMark());
		as.setPincode(address.getPincode());
		as.setAddressType(address.getAddressType());
		as.setLocality(address.getLocality());
		as.setMobile(address.getMobile());
		as.setName(address.getName());
		as.setId(address.getId());
		as.setState(address.getState());
		as.setAlternateMobile(address.getAlternateMobile());
		return as;
	}

	public Address addressRequestToAddress(AddressRequest addressRequest) {
		// return this.modelMapper.map(addressRequest, Address.class);
		Address as = new Address();
		as.setAddressType(addressRequest.getAddressType());
		// System.err.println(addressRequest.getAddressType());
		as.setCity(addressRequest.getCity());
		as.setLandMark(addressRequest.getLandMark());
		as.setPincode(addressRequest.getPincode());
		as.setAddressType(addressRequest.getAddressType());
		as.setLocality(addressRequest.getLocality());
		as.setMobile(addressRequest.getMobile());
		as.setName(addressRequest.getName());
		as.setId(addressRequest.getId());
		as.setState(addressRequest.getState());
		as.setAlternateMobile(addressRequest.getAlternateMobile());
		return as;
	}

	@Override
	public AddressResponse createAdress(AddressRequest addressRequest) {

		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		Address address = this.addressRequestToAddress(addressRequest);
		User user = new User();
		user.setId(appUtils.getUserId());
		address.setUserAddress(user);
		address.setStatus(Boolean.TRUE);
		return this.addressToAddressResponse(this.addressRepo.save(address));
	}

	@Override
	public AddressResponse updateAddress(AddressRequest addressRequest) {
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		Optional<Address> address = Optional.of(this.addressRepo.findById(addressRequest.getId())
				.orElseThrow(() -> new BadRequestException(AppConstant.ADDRESS_NOT_FOUND)));

		Address address2 = address.get();
		address2.setId(addressRequest.getId());
		address2.setLandMark(addressRequest.getLandMark());
		address2.setCity(addressRequest.getCity());
		address2.setLocality(addressRequest.getLocality());
		address2.setMobile(addressRequest.getMobile());
		address2.setPincode(addressRequest.getPincode());
		address2.setAlternateMobile(addressRequest.getAlternateMobile());
		address2.setName(addressRequest.getName());
		address2.setState(addressRequest.getState());
		address2.setAddressType(addressRequest.getAddressType());
		address2.setStatus(Boolean.TRUE);
		User user = new User();
		user.setId(appUtils.getUserId());

		return this.addressToAddressResponse(this.addressRepo.save(address2));

	}

	@Override
	public AddressResponse getbyId(String id) {
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		Address address = this.addressRepo.findById(id)
				.orElseThrow(() -> new BadRequestException(AppConstant.ADDRESS_NOT_FOUND));
		return this.addressToAddressResponse(address);
	}

	@Override
	public boolean deleteAdress(String id) {
//		Optional<Address> findById = this.addressRepo.findById(id);
//		if(findById.isPresent())
//		{
//			Address address = findById.get();
//			address.setStatus(false);
//			this.addressRepo.save(address);
//		}
		if (!appUtils.isUserActive()) {
			throw new BadRequestException(AppConstant.USER_DEACTIVE);
		}
		Address address = this.addressRepo.findByIdAndStatus(id, true)
				.orElseThrow(() -> new BadRequestException(AppConstant.ADDRESS_NOT_FOUND));
		address.setStatus(false);
		this.addressRepo.save(address);

		return true;
	}

	@Override
	public List<AddressResponse> getAddressbyUserid(String id) {
		// TODO Auto-generated method stub
		List<Address> listOfAddresses = this.addressRepo.findAddresssByuserId(id);
		List<AddressResponse> list = listOfAddresses.stream().map(ar -> addressToAddressResponse(ar))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public List<AddressResponse> findByActiveStatus(String id) {
		Optional<User> user = this.userRepo.findById(id);
		List<Address> listOfActiveAddress = this.addressRepo.getActiveAddressOfUser(user.get().getId());
		List<AddressResponse> collect = listOfActiveAddress.stream().map(as -> addressToAddressResponse(as))
				.collect(Collectors.toList());
		return collect;
	}

}
