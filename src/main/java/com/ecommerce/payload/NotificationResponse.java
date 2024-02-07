package com.ecommerce.payload;

import com.ecommerce.model.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse extends AuditResponse {

	private String id;
	private String title;
	private String description;
	@Enumerated(EnumType.STRING)
	private Status status;
	private UserResponse user;
}
