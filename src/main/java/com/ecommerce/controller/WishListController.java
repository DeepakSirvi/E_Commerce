package com.ecommerce.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.WishListProduct;
import com.ecommerce.service.WishListService;

@RestController
@RequestMapping("ecommerce/wishList")
@CrossOrigin
public class WishListController {

	@Autowired

	private WishListService wishlistService;

	@GetMapping("/all")
	public ResponseEntity<Map<String, Object>> getWishlistByUserId() {
		Map<String, Object> wishlistByUserId = wishlistService.getWishlistByUserId();
		return ResponseEntity.ok(wishlistByUserId);

	}
	@PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToWishList(@RequestParam String varientId) { 
        Map<String, Object> response = wishlistService.addToWishList(varientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@DeleteMapping("/remove")
    public ResponseEntity<Map<String, Object>> removeFromWishList(@RequestParam String wishlistId)
             {
		Map<String, Object> response = wishlistService.removeFromWishList(wishlistId);
        return ResponseEntity.ok(response);
        
      }
	
	@DeleteMapping("/dislike")
    public ResponseEntity<Map<String, Object>> dislikeFromWishList(@RequestParam String varientId)
             {
		Map<String, Object> response = wishlistService.dislikeFromWishList(varientId);
        return ResponseEntity.ok(response);
        
      }
	@GetMapping("/VarientExist")
	public ResponseEntity<Map<String, Object>> isVarientExist(@RequestParam String varientId) { 
        Map<String, Object> response = wishlistService.isVarientExist(varientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}	
