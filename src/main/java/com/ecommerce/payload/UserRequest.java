package com.ecommerce.payload;

import com.ecommerce.model.Status;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	private String userId;

	@NotBlank
	@Pattern(regexp = "^\\d{10}$")
	private String userMobile;

	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9.]+@[A-Za-z]+.[A-Za-z]{2,}$")
	private String userEmail;

	@NotBlank
	private String gender;

	@NotBlank
	@Size(min = 4, max = 255)
	private String firstName;
	private String LastName;
	private Status status;

	public UserRequest(String userId) {
		this.userId = userId;
	}
}
