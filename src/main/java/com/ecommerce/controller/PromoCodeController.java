package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.service.PromoCodeService;

@RestController
@RequestMapping("ecommrece/promocode")
@CrossOrigin("*")
public class PromoCodeController {
	@Autowired
 private PromoCodeService codeService;
}
