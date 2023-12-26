package com.ecommerce.payload;

import com.ecommerce.model.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStatusRequest {

	private Long id;
	private Status status;
}
