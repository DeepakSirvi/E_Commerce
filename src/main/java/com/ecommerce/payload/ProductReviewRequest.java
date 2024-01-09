package com.ecommerce.payload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewRequest {

	private Integer numberOfStar;
	private String title;

	private String description;

	private List<MultipartFile> image;

	private Integer userid;

	private String productid;
}
