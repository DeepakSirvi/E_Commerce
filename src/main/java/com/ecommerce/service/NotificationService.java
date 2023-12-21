package com.ecommerce.service;

import java.util.Map;

import com.ecommerce.payload.NotificationRequest;

public interface NotificationService {
	
	public Map<String, Object>  addNotification(NotificationRequest notificationRequest );
	public Map<String, Object>  updateNotification(NotificationRequest notificationRequest );
	public Map<String, Object>  getNotificationById(Long id);
	public Map<String, Object>  deleteNotification(Long id);
	public Map<String, Object>  getAllNotification(Integer page, Integer size, String sort, NotificationRequest notificationRequest);

}
