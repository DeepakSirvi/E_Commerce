package com.ecommerce.payload;

import com.ecommerce.model.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
	
    private Long id;
	private String title;
	private String description;
	private Status status;
	private Long userId;
}
