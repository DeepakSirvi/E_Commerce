package com.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.payload.PromoCodeRequest;
import com.ecommerce.payload.PromoCodeResponse;
import com.ecommerce.repository.PromoCodeRepo;
import com.ecommerce.service.PromoCodeService;
@Service
public class ProductCodeImpl  implements PromoCodeService{

	@Autowired
	private PromoCodeRepo codeRepo;
	@Override
	public PromoCodeResponse addPromoCode(PromoCodeRequest codeRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
