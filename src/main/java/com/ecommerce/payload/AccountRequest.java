package com.ecommerce.payload;

import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AccountRequest {
	
	@NotBlank
	private String accountNumber;
	@NotBlank
	private String accountHolderName;
	@NotBlank
	private String bankName;
	@NotBlank
	private String bankIFSCcode;
	private String venderGSTnumber;
	@NotBlank
	private String panNumber;
	private Status status;
	private UserRequest user;





}
