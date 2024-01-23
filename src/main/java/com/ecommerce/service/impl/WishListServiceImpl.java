package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
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
public class WishListServiceImpl implements WishListService {

	private static final String ID = null;

	@Autowired
	private WishListRepo wishListRepo;

	@Autowired
	private AppUtils appUtils;

	@Autowired

	private UserRepo userRepo;

	@Override
	public Map<String, Object> addToWishList(String varientId, String userId) {

		Map<String, Object> response = new HashMap<>();

		boolean productExists = wishListRepo.existsByVarientIdAndUserId(varientId, userId);

		if (productExists) {
			response.put("response", AppConstant.PRODUCT_ALREADY_IN_WISHLIST);
			throw new BadRequestException(AppConstant.PRODUCT_NOT_ADD_WISHLIST);
		} else {

			Varient varient = new Varient();
			varient.setId(varientId);

			User user = new User();
			user.setId(userId);

			WishListProduct wishListProduct = new WishListProduct();
			wishListProduct.setVarient(varient);
			wishListProduct.setUser(user);

			wishListRepo.save(wishListProduct);

			response.put("response", AppConstant.ADDWISHLIST);
			return response;
		}
	}

	@Override
	public Map<String, Object> removeFromWishList(String wishlistId) {

		Map<String, Object> response = new HashMap<>();

		if (wishListRepo.existsById(wishlistId)) {

			wishListRepo.deleteById(wishlistId);

			response.put("response", AppConstant.REMOVE_FROM_WISHLIST);

		} else {

			throw new ResourceNotFoundException(AppConstant.WISHLIST, ID, wishlistId);
		}

		return response;
	}

	@Override
	public Map<String, Object> getWishlistByUserId(String userId) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER, ID, userId));

		List<WishListProduct> wishListProducts = wishListRepo.findByUserId(user);
		Map<String, Object> response = new HashMap<>();
		response.put("message", wishListProducts);
		
		response.put("respo",AppConstant.WISHLIST_RETRIVED_SUCCESSFULLY);
		

		return response;
	}

}
