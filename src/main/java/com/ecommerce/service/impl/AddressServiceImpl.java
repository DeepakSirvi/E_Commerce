package com.ecommerce.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Address;
import com.ecommerce.payload.AddressRequest;
import com.ecommerce.payload.AddressResponse;
import com.ecommerce.repository.AddressRepo;
import com.ecommerce.service.AddressService;
@Service
public class AddressServiceImpl implements AddressService {
@Autowired
private AddressRepo addressRepo;
@Autowired
private ModelMapper modelMapper;


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
	
	return this.addressToAddressResponse(this.addressRepo.save(address));
}
	

}
