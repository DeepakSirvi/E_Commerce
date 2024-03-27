package com.ecommerce.service;

import com.ecommerce.payload.PromoCodeRequest;
import com.ecommerce.payload.PromoCodeResponse;

public interface PromoCodeService {
    public  PromoCodeResponse addPromoCode(PromoCodeRequest codeRequest);
}
