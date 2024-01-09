package com.ecommerce.payload;

import java.util.HashSet;
import java.util.Set;

import com.ecommerce.model.ProductReview;
import com.ecommerce.model.ReviewImage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class ProductReviewRespomce {
	private Integer numberOfStar;
	private String title;

	private String description;

	private Set<ReviewImage> image=new HashSet<>();

	public ProductReviewRespomce(Integer numberOfStar, String title, String description, Set<ReviewImage> image) {
		super();
		this.numberOfStar = numberOfStar;
		this.title = title;
		this.description = description;
		image.forEach(obj -> {
			ReviewImage img = new ReviewImage();
			img.setId(obj.getId());
			img.setImageName(obj.getImageName());
//			ProductReview imageReview = img.getImageReview();
//			imageReview.setImage(obj.getImageReview().getImage());
//			imageReview.setId(obj.getImageReview().getId());
//			img.setImageReview(imageReview);
			this.image.add(img);
		});
	}

}