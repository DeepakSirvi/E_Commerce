package com.ecommerce.controller;

import java.util.Collections;
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
	
	private  WishListService  wishlistService;
	
	@PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToWishList( @RequestParam Long varientId,@RequestParam Long userId) {    
        Map<String, Object> response = wishlistService.addToWishList(varientId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@DeleteMapping("/remove")
    public ResponseEntity<Map<String, Object>> removeFromWishList( @RequestParam Long varientId,@RequestParam Long userId)
             {
		Map<String, Object> response = wishlistService.removeFromWishList(varientId, userId);
        return ResponseEntity.ok(response);
        
      }
	
	@GetMapping("/activeVarient/{userId}")
    public ResponseEntity<Map<String, Object>> getActiveVarientInWishlistByUserId(@PathVariable Long userId) {
        Map<String, Object> response = wishlistService.getActiveVarientInWishlistByUserId(userId);
        return  new ResponseEntity<>(response, HttpStatus.OK) ;
    }
	}
	  
	
	
	
	
