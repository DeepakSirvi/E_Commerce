package com.ecommerce.payload;

import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
	
	private String accountNumber;
	private String accountHolderName;
	private String bankName;
	private String bankIFSCcode;
	private String venderGSTnumber;
	private String panNumber;
	private Status status;
	private UserRequest user;




}
