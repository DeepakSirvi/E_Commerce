package com.ecommerce.model;

public enum RoleName {
	ADMIN("This role is for  who have all authority"), CUSTOMER("This role is for customer who is here for shopping"),
	VENDOR("This role is for vender who is here to sell his product");

	String description;

	RoleName(String description) {
		this.description = description;
	}

	RoleName() {
		this.description = "Role of providing authority";
	}

	public String getDescription() {
		return description;
	}

}
