package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Address;
import com.ecommerce.payload.AddressRequest;
import com.ecommerce.payload.AddressResponse;
import com.ecommerce.service.AddressService;
@RestController
@RequestMapping("/ecommerce/address")
@CrossOrigin
public class AddressController {
@Autowired
	private AddressService addressService;
@PostMapping("/")
	public  ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest addressRequest){
		return new ResponseEntity<AddressResponse>(this.addressService.createAdress(addressRequest),HttpStatus.CREATED);
	}
}
