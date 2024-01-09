package com.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
private ModelMapper modelMapper;
@Autowired
private AppUtils appUtils;
@Autowired
private UserRepo userRepo;
  

public ApiResponse apiresponse;

public AddressResponse addressToAddressResponse(Address address)
{ 
	return this.modelMapper.map(address, AddressResponse.class);
}
public Address addressRequestToAddress(AddressRequest addressRequest)
{
	return this.modelMapper.map(addressRequest, Address.class);
}



@Override
public AddressResponse createAdress(AddressRequest addressRequest) {

	Address address=this.addressRequestToAddress(addressRequest);
	//System.out.println(addressRequest.getLocality());
	User user=new User();
	
	user.setId(appUtils.getUserId());
	
	address.setUserAddress(user);
	address.setStatus(Boolean.TRUE);
	
	return this.addressToAddressResponse(this.addressRepo.save(address));
}
@Override
public AddressResponse updateAddress(AddressRequest addressRequest) {
	System.out.println("-------------");
	Optional<Address> address = Optional.of(this.addressRepo.findById(addressRequest.getId()).orElseThrow(()->new BadRequestException(AppConstant.ADDRESS_NOT_FOUND)));
	
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
	address2.setStatus(Boolean.TRUE);
	User user=new User();
	user.setId(appUtils.getUserId());
	
	return this.addressToAddressResponse(this.addressRepo.save(address2));
	
	
}

@Override
public AddressResponse getbyId(String id) {
	Address address=this.addressRepo.findById(id).orElseThrow(()->new BadRequestException(AppConstant.ADDRESS_NOT_FOUND)) ;
	return this.addressToAddressResponse(address);
}

@Override
public boolean deleteAdress(String id) {
	Address address=this.addressRepo.findByIdAndStatus(id,true).orElseThrow(()->new BadRequestException(AppConstant.ADDRESS_NOT_FOUND));
	this.addressRepo.save(address);
	return true;
}

@Override
public List<AddressResponse> getAddressbyUserid(String id) {
	// TODO Auto-generated method stub
	List<Address> listOfAddresses = this.addressRepo.findAddresssByuserId(id);
	List<AddressResponse> list = listOfAddresses.stream().map(ar->addressToAddressResponse(ar)).collect(Collectors.toList());
			
	return list;
}
@Override
public List<AddressResponse> findByActiveStatus(String id) {
 Optional<User> user= this.userRepo.findById(id);
 List<Address> listOfActiveAddress= this.addressRepo.getActiveAddressOfUser(user.get().getId());
 List<AddressResponse> collect = listOfActiveAddress.stream().map(as->addressToAddressResponse(as)).collect(Collectors.toList());
 return collect;
}



}
