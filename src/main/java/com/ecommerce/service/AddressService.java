package com.ecommerce.service;

import com.ecommerce.model.Address;
import com.ecommerce.payload.AddressRequest;
import com.ecommerce.payload.AddressResponse;

public interface AddressService {

	public AddressResponse createAdress(AddressRequest addressRequest);
	
	
	
}
