package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.model.WishListProduct;




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
		        response.put("response", AppConstant.PRODUCT_ADD_TO_WISHLIST);
		        throw new BadRequestException(AppConstant.PRODUCT_NOT_ADD_WISHLIST);
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
	public Map<String, Object> removeFromWishList(Long wishlistId) {
		
		Map<String, Object> response = new HashMap<>();
		
		Optional<WishListProduct> wishlistProductOptional = wishListRepo.findById(wishlistId);
		if (wishlistProductOptional.isPresent()) {
			 wishListRepo.deleteById(wishlistId);
			 response.put("response", AppConstant.REMOVE_FROM_WISHLIST_);
		} else {
            throw new BadRequestException(AppConstant.REMOVE_NOT_FROM_WISHLIST_);
    }
        return response;			
	}
	
	@Override
	public List<WishListProduct>  getWishlistByUserId(Long userId) {
         User u =this.userRepo.findById(userId).orElseThrow(() -> new BadRequestException(AppConstant.USER_NOT_FOUND));
         {
	     List<WishListProduct> wishListProduct = this.wishListRepo.findByUser(u);
	     return wishListProduct;
  }
         
		
	}

	
}

	

	         
	
		
	      
		
		
	

	
	

	

