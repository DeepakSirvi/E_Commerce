package com.ecommerce.payload;

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
public class AddressRequest {
	
	private String name;
	private String mobile;
	private Integer pincode;
	private String locality;
	private String city;
	private String state;
	private String landMark;
	private String alternateMobile;
	private String addressType;

}
