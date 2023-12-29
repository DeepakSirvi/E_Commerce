package com.ecommerce.payload;


import com.ecommerce.model.User;
import com.ecommerce.model.Varient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductSaveForLaterRequest {
private String id;
private String varientid;

}
