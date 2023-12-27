package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.AddressRequest;
import com.ecommerce.payload.AddressResponse;
import com.ecommerce.service.AddressService;
@RestController
@RequestMapping("/ecommerce/address")
@CrossOrigin
public class AddressController {
@Autowired
	private AddressService addressService;
@PostMapping("/create")
public  ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest addressRequest){
	return new ResponseEntity<AddressResponse>(this.addressService.createAdress(addressRequest),HttpStatus.CREATED);
}
@PutMapping("/update/{id}")
public ResponseEntity<AddressResponse> updateAddress(@RequestBody AddressRequest addressRequest, @PathVariable String id){
return new ResponseEntity<AddressResponse>(this.addressService.updateAddress(addressRequest),HttpStatus.OK);
}
@GetMapping("/{id}")
public ResponseEntity<AddressResponse> getByAddressid(@PathVariable String id){
return new ResponseEntity<AddressResponse>(this.addressService.getbyId(id),HttpStatus.OK);
}
@DeleteMapping("/{id}")
public ResponseEntity<Boolean> deleteById(@PathVariable String id){
return ResponseEntity.ok(this.addressService.deleteAdress(id));
}
@GetMapping("/GetActiveAddressByUserId/{id}" )
public ResponseEntity<List<AddressResponse>>findStatusByUserId(@PathVariable String id){
	return new ResponseEntity<List<AddressResponse>>(this.addressService.findByActiveStatus(id),HttpStatus.OK);
}
@GetMapping("/GetAddressByUserId/{id}")
public ResponseEntity <List<AddressResponse>>findAddressbyUserId(@PathVariable String id ){
return new  ResponseEntity<List<AddressResponse>>( this.addressService.getAddressbyUserid(id),HttpStatus.OK);
}
}
