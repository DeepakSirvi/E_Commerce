package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.service.CartService;
import com.ecommerce.util.AppUtils;

@RestController
@RequestMapping("/ecommerce/cart")
@CrossOrigin
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private AppUtils appUtils;

	@PostMapping("/addTocart/{varientId}/{quantity}")
	public ResponseEntity<?> addToCart(@PathVariable(value = "varientId") String id,
			@PathVariable(value = "quantity") short quantity) {
		return new ResponseEntity<Map<String, Object>>(cartService.addProductToCart(id, quantity), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<?> getCartByUser() {
		return new ResponseEntity<Map<String, Object>>(cartService.getCartByUserId(appUtils.getUserId()),
				HttpStatus.OK);
	}

	@DeleteMapping("/{cartId}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "cartId") String cartId) {
		return new ResponseEntity<Map<String, Object>>(cartService.deleteCartById(cartId), HttpStatus.OK);
	}

	@GetMapping("/customer/navbarCount")
	public ResponseEntity<?> getTotalCountForNavbar() {
		return new ResponseEntity<Map<String, Object>>(cartService.getCount(), HttpStatus.OK);
	}

}
