package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.service.CartService;

@RestController
@RequestMapping("/ecommerce/cart")
@CrossOrigin
public class CartController {
	
	@Autowired
	private CartService cartService; 
	
	@PostMapping("/addTocart/{varientId}/{quantity}")
	public ResponseEntity<?> addToCart(
			@PathVariable(value = "varientId") Long id,
			@PathVariable(value = "quantity") short quantity)
	{
	return new ResponseEntity<Map<String,Object>>(cartService.addProductToCart(id,quantity),HttpStatus.OK);	
	}
	
	
 
}
