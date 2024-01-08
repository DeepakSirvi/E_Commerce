package com.ecommerce.service;

import java.util.List;

import com.ecommerce.model.Address;
import com.ecommerce.payload.AddressRequest;
import com.ecommerce.payload.AddressResponse;

public interface AddressService {

	public AddressResponse createAdress(AddressRequest addressRequest);
	public AddressResponse updateAddress(AddressRequest addressRequest);
	public AddressResponse getbyId(String id);
	public boolean deleteAdress(String id);
	public List <AddressResponse >getAddressbyUserid(String id);
	public  List <AddressResponse>  findByActiveStatus( String id); 
	
	
	
	
	
}
