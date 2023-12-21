package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.model.WishListProduct;
import com.ecommerce.payload.VarientResponse;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.repository.WishListRepo;
import com.ecommerce.service.WishListService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class WishListServiceImpl  implements WishListService   {
	
	

	@Autowired
	private WishListRepo wishListRepo ;
	 
	@Autowired
	private AppUtils  appUtils;
	
	@Autowired
	
	private UserRepo userRepo;

	@Override
	public Map<String, Object> addToWishList(Long varientId, Long userId) {
		
		 Map<String, Object> response = new HashMap<>();
		 
		 boolean productExists = wishListRepo.existsByVarientIdAndUserId(varientId, userId);
		 
		 if (productExists) {
		        response.put("response", AppConstant.PRODUCT_ALREADY_IN_WISHLIST);
		        throw new BadRequestException("Product is already add  wishlist.");
		    } else {

		 Varient  varient  = new Varient();
		 varient.setId(varientId);
		 
		 User user = new User();
		 user.setId(userId);
		 
		 WishListProduct wishListProduct = new  WishListProduct();
		 wishListProduct.setVarient(varient);
		 wishListProduct.setUser(user);
		 
		 wishListRepo.save(wishListProduct);
		 
		 response.put("response", AppConstant.ADDWISHLIST);
		 return response;
	}
		

	
	}

	@Override
	public Map<String, Object> removeFromWishList(Long varientId, Long userId) {
		
		 Map<String, Object> response = new HashMap<>();
		 
		 boolean productExists = wishListRepo.existsByVarientIdAndUserId(varientId, userId);
               
		 if (productExists) {
			 
			 
		 wishListRepo.existsByVarientIdAndUserId(varientId, userId);
	        response.put("response", AppConstant.REMOVE_FROM_WISHLIST_);
	    } else {
	       
	        response.put("response", AppConstant.PRODUCT_NOT_ADD_WISHLIST);
	        throw new BadRequestException("Product is not add to wishlist.");
	    }

	    return response;
	}



  @Override
  public Map<String, Object> getActiveVarientInWishlistByUserId(Long userId) {
    Map<String, Object> response = new HashMap<>();
    

    try {
    	  Optional<User> userOptional = userRepo.findById(userId);
    	  if (userOptional.isPresent()) {
              User user = userOptional.get();
             
              Optional<WishListProduct> wishlist = wishListRepo.findByUser(new User(userId));
             if (wishlist.isPresent()) {
            	 
                   
               } else {
                  response.put("responses", AppConstant.WISHLIST_NOT_FOUND);
              }
           } else {
              response.put("responses", AppConstant.USER_NOT_FOUND);
          }
      } catch (Exception e) {
          response.put("error", e.getMessage());
      }

      return response;
    }

     private VarientResponse varientToVarientResponse(Varient varient) {
      VarientResponse varientResponse = new VarientResponse();
      varientResponse.setId(varient.getId());
      return varientResponse;
  }
      
}
               
	
	


	

	         
	
		
	      
		
		
	

	
	

	

