package com.ecommerce.service;

import java.util.List;

import com.ecommerce.model.Address;
import com.ecommerce.payload.AddressRequest;
import com.ecommerce.payload.AddressResponse;

public interface AddressService {

	public AddressResponse createAdress(AddressRequest addressRequest);
	public AddressResponse updateAddress(AddressRequest addressRequest);
	public AddressResponse getbyId(Long id);
	public boolean deleteAdress(Long id);
	public List <AddressResponse >getAddressbyUserid(Long id);
	public  List <AddressResponse>  findByActiveStatus( Long id); 
	
	
	
	
	
}
