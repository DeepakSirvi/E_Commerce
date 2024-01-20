package com.ecommerce.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.UpdateStatusRequest;
import com.ecommerce.payload.VarientRequest;
import com.ecommerce.service.VarientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("ecommerce/productVarient")
@CrossOrigin
public class VarientPController {

	@Autowired
	private VarientService varientService;

	
	@PostMapping(path="/")
	public ResponseEntity<Map<String, Object>> addProductVarient(@RequestPart(value = "varientRequest") String varientRequest,
		
			@RequestPart(value="file",required = false) List<MultipartFile> multipartFiles){
		System.out.println(multipartFiles);
		VarientRequest request=null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			request=mapper.readValue(varientRequest, VarientRequest.class);
			System.err.println("error");
		}
		catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			System.out.println("sdf");
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			System.out.println("3123");
			e.printStackTrace();
		}
		catch(Exception e) {
			System.out.println("asda");
		}
				System.out.println(request.getVarientName());
		return new ResponseEntity<Map<String,Object>>(varientService.createVarient(request,multipartFiles),HttpStatus.CREATED);

	}

	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> updateProductVarient(@RequestBody VarientRequest varientRequest) {

		return new ResponseEntity<Map<String, Object>>(varientService.updateVarient(varientRequest),
				HttpStatus.CREATED);
	}

	@GetMapping("/{varientId}")
	public ResponseEntity<Map<String, Object>> getProductVarient(@PathParam(value = "varientId") String id) {

		return new ResponseEntity<Map<String, Object>>(varientService.getVarient(id), HttpStatus.CREATED);
	}

	@GetMapping("/AllVarient/{productId}")
	public ResponseEntity<Map<String, Object>> getAllProductVarient(@PathParam(value = "productId") String id) {

		return new ResponseEntity<Map<String, Object>>(varientService.getAllVarientByProductId(id), HttpStatus.CREATED);
	}

	@PatchMapping("/admin/status")
	public ResponseEntity<Map<String, Object>> updateVarientStatus(@RequestBody UpdateStatusRequest statusRequest) {

		return new ResponseEntity<Map<String, Object>>(varientService.updateVarientStatus(statusRequest), HttpStatus.CREATED);
	}

	@GetMapping("/permitAll/varientByProduct/{productId}")
	public ResponseEntity<Map<String, Object>> getActiveVarientByProductId(@PathVariable(value = "productId") String productId) {
		return new ResponseEntity<Map<String, Object>>(varientService.getActiveOneVarientByProductId(productId), HttpStatus.CREATED);
	}
	
	@PostMapping("/permitAll/catJoin/{attributeId}/{productId}")
	public ResponseEntity<Map<String, Object>> getActiveVarientByCatJoin(@RequestBody List<String> attributeJoin,
			@PathVariable(name = "attributeId") String attributeId,
			@PathVariable(name = "productId") String productId) {
		return new ResponseEntity<Map<String, Object>>(varientService.getActiveVarientByCat(attributeJoin,attributeId,productId), HttpStatus.CREATED);
	}

}
