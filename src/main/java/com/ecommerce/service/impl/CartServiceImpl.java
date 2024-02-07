package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.VARIENT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.payload.CartResponse;
import com.ecommerce.repository.CartRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.repository.VarientRepo;
import com.ecommerce.service.CartService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private VarientRepo varientRepo;
	
	@Autowired
	private AppUtils appUtils;

	@Override
	public Map<String, Object> addProductToCart(String id, short quantity) {
		varientRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(VARIENT, ID, id));
		Optional<Cart> cart = cartRepo.findByUserAndVarient(new User(appUtils.getUserId()), new Varient(id));
		Cart cart2 = new Cart();
		if (cart.isPresent()) {
			cart2 = cart.get();
			Integer q = cart2.getQuantity() + quantity;
			if (q > cart2.getVarient().getStock())
				throw new BadRequestException(AppConstant.AVAILABLE_STOCK + " " + cart2.getVarient().getStock());

			cart2.setQuantity(q);
			cart2 = cartRepo.save(cart2);
		} else {
			cart2.setQuantity((int) quantity);
			cart2.setUser(new User(appUtils.getUserId()));
			cart2.setVarient(new Varient(id));
			cart2 = cartRepo.save(cart2);
		}
		Map<String, Object> response = new HashMap<>();
		response.put(AppConstant.MESSAGE,AppConstant.ADD_TO_CART);
		return response;
	}

	@Override
	public Map<String, Object> getCartByUserId(String userId) {
		Map<String, Object> response = new HashMap<>();
	    User user = userRepo.findByIdAndStatus(userId,Status.ACTIVE).orElseThrow(()->new ResourceNotFoundException(AppConstant.USER,AppConstant.ID,userId));

		List<Cart> cartList = cartRepo.findByUser(new User(userId));
		List<CartResponse> cartResponses = cartList.stream().map(carts -> {
			return new CartResponse().cartToCartResponse(carts);
		}).collect(Collectors.toList());
		response.put("cart", cartResponses);
		return response;
	}

	@Override
	public Map<String, Object> deleteCartById(String cartId) {
		Map<String , Object> response = new HashMap<>();
		Optional<Cart> cart = cartRepo.findById(cartId);
		if(cart.isPresent()) {
			if(cart.get().getUser().getId().equals(appUtils.getUserId())) {
			cartRepo.deleteById(cartId);
			response.put(AppConstant.MESSAGE, AppConstant.CART_ITEM_REMOVE);
			return response;
			}
			else
			{
				throw new UnauthorizedException(AppConstant.UNAUTHORIZED);
			}
		}
		else
		{
			throw new ResourceNotFoundException(AppConstant.CART,AppConstant.ID,cartId);
		}	
	}

	@Override
	public Map<String, Object> getCount() {
		Map<String, Object> response = new HashMap<>();
		List<Object> count=cartRepo.fetchCountofWishAndCartItem(appUtils.getUserId());
		response.put(AppConstant.Count, count);
		return response;
	}
}
