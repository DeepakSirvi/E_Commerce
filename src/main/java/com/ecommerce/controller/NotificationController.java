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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.NotificationRequest;
import com.ecommerce.service.NotificationService;
import com.ecommerce.util.AppConstant;

@RestController
@RequestMapping("/ecommerce/notification")
@CrossOrigin
public class NotificationController {
	
	
	@Autowired
	private NotificationService notificationService;
	
		
	@PostMapping("/admin")
	public ResponseEntity<?> createNotification(@RequestBody NotificationRequest notificationRequest)
	{
		return new ResponseEntity<Map<String,Object>>(notificationService.addNotification(notificationRequest),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> createNotification(@PathVariable(name = "id") String id)
	{
		return new ResponseEntity<Map<String,Object>>(notificationService.getNotificationById(id),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNotification(@PathVariable(name = "id") String id)
	{
		return new ResponseEntity<Map<String,Object>>(notificationService.deleteNotification(id),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllNotification(@RequestBody NotificationRequest notificationRequest,
			@RequestParam(value = "page", required = false, defaultValue =  AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size,
			@RequestParam(value="sort",required=false, defaultValue = AppConstant.DEFAULT_SORT_DIR) String sort)
	{
		return new ResponseEntity<Map<String,Object>>(notificationService.getAllNotification(page,size,sort,notificationRequest),HttpStatus.CREATED);
	}
	
}
