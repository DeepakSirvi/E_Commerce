package com.ecommerce.service;

import org.springframework.stereotype.Service;

import com.ecommerce.payload.PromoCodeRequest;
import com.ecommerce.payload.PromoCodeResponce;

public interface PromoCodeService {
    public  PromoCodeResponce addPromoCode(PromoCodeRequest codeRequest);
}
