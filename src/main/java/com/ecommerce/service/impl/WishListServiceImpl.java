package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.VARIENT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.model.WishListProduct;
import com.ecommerce.payload.VarientResponse;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.repository.VarientRepo;
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
	
	@Autowired
	private VarientRepo varientRepo;

	@Override
	public Map<String, Object> addToWishList(String varientId) {
		 Varient varient = varientRepo.findById(varientId).orElseThrow(() -> new ResourceNotFoundException(VARIENT, ID, varientId));
		 if(varient.getStatus().equals(Status.DEACTIVE))
		 {
			 throw new BadRequestException(AppConstant.VARIENT_INACTIVE);
		 }
		 Map<String, Object> response = new HashMap<>(); 
		 boolean productExists = wishListRepo.existsByVarientIdAndUserId(varientId,appUtils.getUserId());
		 
		 if (productExists) {
		      response.put("response", AppConstant.ALREADY_ADDED);
				 return response;
		    } else {
		 
		 User user = new User(appUtils.getUserId());
		 WishListProduct wishListProduct = new  WishListProduct();
		 wishListProduct.setVarient(varient);
		 wishListProduct.setUser(user);
		 
		 wishListRepo.save(wishListProduct);
		 
		 response.put("response", AppConstant.ADDWISHLIST);
		 return response;
	}
	}

	
	@Override
	public Map<String, Object> removeFromWishList(String wishlistId) {
		
		Map<String ,Object> response = new HashMap<>();
		
		 if (wishListRepo.existsById(wishlistId)) {
			 
			 wishListRepo.deleteById(wishlistId);
			 
			 response.put("response", AppConstant.REMOVE_FROM_WISHLIST);
		 
	} else {
        
        throw new ResourceNotFoundException(AppConstant.WISHLIST ,AppConstant.ID, wishlistId);
    }

    return response;
}


	@Override
	public List<WishListProduct> getWishlistByUserId(String userId) {
		
		 User user = userRepo.findById(userId)
		            .orElseThrow(() -> new ResourceNotFoundException( AppConstant.USER ,AppConstant.ID ,userId));

		 List<WishListProduct> wishListProducts = wishListRepo.findByUserId(user);
	        return wishListProducts;
	    }
	
		
	}
		

               
	
	


	

	         
	
		
	      
		
		
	

	
	

	

