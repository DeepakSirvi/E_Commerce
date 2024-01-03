package com.ecommerce.service.impl;

import static com.ecommerce.util.AppConstant.ID;
import static com.ecommerce.util.AppConstant.NOTIFICATION;
import static com.ecommerce.util.AppConstant.NOTIFICATION_ADDED;
import static com.ecommerce.util.AppConstant.NOTIFICATION_DELETED;
import static com.ecommerce.util.AppConstant.NOTIFICATION_TITLE_TAKEN;
import static com.ecommerce.util.AppConstant.NOTIFICATION_UPDATE;
import static com.ecommerce.util.AppConstant.RESPONSE_MESSAGE;
import static com.ecommerce.util.AppConstant.UNAUTHORIZED;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.exception.UnauthorizedException;
import com.ecommerce.model.Notifications;
import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.NotificationRequest;
import com.ecommerce.payload.NotificationResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.UserResponse;
import com.ecommerce.repository.NotificationRepo;
import com.ecommerce.repository.UserRoleRepo;
import com.ecommerce.service.NotificationService;
import com.ecommerce.util.AppUtils;
import com.ecommerce.util.RoleNameIdConstant;

@Service
public class NotificationServiceImpl implements NotificationService {

	

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private AppUtils appUtils;

	@Autowired
	private UserRoleRepo userRepo;

	
//	For add notification

	@Override
	public Map<String, Object> addNotification(NotificationRequest notificationRequest) {
		Map<String, Object> response = new HashMap<>();
		if (notificationRepo.existsByTitle(notificationRequest.getTitle())) {
			throw new BadRequestException( NOTIFICATION_TITLE_TAKEN);
		}
		Notifications notifications = modelMapper.map(notificationRequest, Notifications.class);
		notifications.setUser(new User(appUtils.getUserId()));
		notifications.setStatus(Status.ACTIVE);
		notificationRepo.save(notifications);
		response.put(RESPONSE_MESSAGE, NOTIFICATION_ADDED);
		return response;

	}

	@Override
	public Map<String, Object> updateNotification(NotificationRequest notificationRequest) {
		Map<String, Object> response = new HashMap<>();
		if (notificationRepo.existsByTitle(notificationRequest.getTitle())) {
			throw new BadRequestException( NOTIFICATION_TITLE_TAKEN);
		}
		Notifications notification = notificationRepo.findById(notificationRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException(NOTIFICATION, ID, notificationRequest.getId()));
		if(userRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleNameIdConstant.ADMIN)) 
				|| notification.getCreatedBy().equals(appUtils.getUserId())) {
        
		notification.setTitle(notificationRequest.getTitle());
        notification.setDescription(notificationRequest.getDescription());
        notificationRepo.save(notification);
		response.put(RESPONSE_MESSAGE, NOTIFICATION_UPDATE);
		return response;
		}
		throw new UnauthorizedException( UNAUTHORIZED);
	}

	@Override
	public Map<String, Object> getNotificationById(String id) {
		Map<String, Object> response = new HashMap<>();
		Notifications notification = notificationRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NOTIFICATION, ID, id));
		if (notification.getStatus().equals(Status.ACTIVE) ||
				notification.getCreatedBy().equals(appUtils.getUserId()) ||
				userRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleNameIdConstant.ADMIN))) {
			NotificationResponse notificationResponse = notificationToNotificationResponse(notification);
			response.put(NOTIFICATION, notificationResponse);
			return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

	@Override
	public Map<String, Object> deleteNotification(String id) {
		Map<String, Object> response = new HashMap<>();
		Notifications notification = notificationRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(NOTIFICATION, ID, id));

		if (notification.getCreatedBy().equals(appUtils.getUserId())
				|| userRepo.existsByUserAndRole(new User(appUtils.getUserId()), new Role(RoleNameIdConstant.ADMIN))) {
			notification.setStatus(Status.DEACTIVE);
			notificationRepo.save(notification);
			response.put(RESPONSE_MESSAGE, NOTIFICATION_DELETED);
			return response;
		}
		throw new UnauthorizedException(UNAUTHORIZED);
	}

	@Override
	public Map<String, Object> getAllNotification(Integer page, Integer size, String sort,
			NotificationRequest notificationRequest) {
		Map<String, Object> response = new HashMap<>();
		Notifications notification = modelMapper.map(notificationRequest ,Notifications.class);
		Example<Notifications> example = Example.of(notification);
		Sort sort1=null;
        if(sort.equals("DESC"))
        {
        	
        	 sort1 =Sort.by(Sort.Order.desc("updatedAt"));
        }
        else
        {
        	 sort1 =Sort.by(Sort.Order.asc("updatedAt"));
        }
		
		Pageable pageable = PageRequest.of(page,size,sort1);	
	    Page<Notifications> notificationSet =notificationRepo.findAll(example,pageable);		
	    
	    Set<NotificationResponse> notificationResponses = notificationSet.getContent().stream().map(notifications -> notificationToNotificationResponse(notifications)
	    		).collect(Collectors.toSet());
		PageResponse<NotificationResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(notificationResponses);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(notificationSet.getNumberOfElements());
		pageResponse.setTotalPages(notificationSet.getTotalPages());
		pageResponse.setLast(notificationSet.isLast());
		pageResponse.setFirst(notificationSet.isFirst());
		return response;
	}

	private NotificationResponse notificationToNotificationResponse(Notifications notifications) {
		NotificationResponse notificationResponse=new NotificationResponse();
		notificationResponse.setTitle(notifications.getTitle());
		notificationResponse.setDescription(notifications.getDescription());
		notificationResponse.setCreatedAt(notifications.getCreatedAt());;
		
		UserResponse user = new UserResponse();
		user.setId(notifications.getUser().getId());	
		user.setFirstName(notifications.getUser().getFirstName());
		user.setLastName(notifications.getUser().getLastName());
		notificationResponse.setUser(user);;
		return notificationResponse;
	}

}
