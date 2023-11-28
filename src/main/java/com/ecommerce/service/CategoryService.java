package com.ecommerce.service;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.CategoryRequest;

public interface CategoryService {

   public ApiResponse addCategory(CategoryRequest categoryRequest);

}
