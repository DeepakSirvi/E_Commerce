package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.ProductSaveForLaterRequest;
import com.ecommerce.payload.ProductSaveForLaterResponse;
import com.ecommerce.service.ProductSaveForLaterService;

@RestController
@RequestMapping("/ecommerce/productsaveforlater")
@CrossOrigin
public class ProductSaveForLaterController {

	@Autowired
    private ProductSaveForLaterService forLaterService;


@PostMapping("/create/{vid}")
public ResponseEntity<?> addProductSaveForLater(@PathVariable String vid){
	return new ResponseEntity<>(this.forLaterService.addProductSaveforLater(vid),HttpStatus.CREATED);
}
@GetMapping("/allSaveForLaterUserId")
public ResponseEntity<Map<String, Object>> getAllAccountsByUserId() {

	return new ResponseEntity<Map<String, Object>>(forLaterService.getAllSaveForLaterByUserId(),HttpStatus.OK);
}
@DeleteMapping("/delete/{id}")
public ResponseEntity<?>DeleteProductSaveForLater(@PathVariable String id){

	return forLaterService.deleteSaveForLater(id);
}
}
