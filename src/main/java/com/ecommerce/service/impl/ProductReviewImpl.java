package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Orders;
import com.ecommerce.model.ProductReview;
import com.ecommerce.model.ReviewImage;
import com.ecommerce.model.User;
import com.ecommerce.payload.ProductReviewRespomce;
import com.ecommerce.repository.OrderRepo;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.repository.ProductReviewRepo;
import com.ecommerce.repository.UserRepo;
import com.ecommerce.service.ProductReviewService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class ProductReviewImpl implements ProductReviewService {
	@Autowired
	private ProductRepo productRepo;
	@SuppressWarnings("unused")
	@Autowired
	private UserRepo userrepo;
	@Autowired
	private ProductReviewRepo productReviewRepo;

	@Autowired
	private AppUtils appUtils;
	@Autowired
	private OrderRepo orderRepo;

	@Value("${app.path}")
	private String FILE_PATH;

	@Override
	public ResponseEntity<?> addProductReview(Integer numberOfStar, String description, String productid,
			List<MultipartFile> image, String title) {
		Map<String, Object> map = new HashMap<>();

		 User user = userrepo.findById(appUtils.getUserId()).get();
		List<Orders> order2 = user.getOrder();

		boolean isPresent = order2.stream()
			    .flatMap(obj -> obj.getOrderItem().stream())
			    .anyMatch(e -> e.getId() == productid);


		if (!isPresent) {
			map.put(AppConstant.MESSAGE, AppConstant.PLESE_ORDER_PRODUCT);
			return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
		}
		user.setId(appUtils.getUserId());

		ProductReview productReview = new ProductReview();
		productReview.setUser(user);
		productReview.setProduct(productRepo.findById(productid).get());
		productReview.setDescription(description);
		productReview.setNumberOfStar(numberOfStar);
		productReview.setTitle(title);
		if (!image.isEmpty()) {
			List<ReviewImage> image2 = productReview.getImage();
			for (MultipartFile file : image) {
				String uploadImage = appUtils.uploadImage(file, AppConstant.PRODUCT_IMAGE_PATH, null);

				ReviewImage r = new ReviewImage();
				r.setImageReview(productReview);
				r.setImageName(uploadImage);

				image2.add(r);

			}
			productReview.setImage(image2);
		}
		ProductReview save = productReviewRepo.save(productReview);

		map.put("data", new ProductReviewRespomce(save.getNumberOfStar(), save.getTitle(), save.getDescription(),
				save.getImage()));
		map.put(AppConstant.MESSAGE, AppConstant.PRODUCT_ADDED_SUCCESS);

		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> deleteProductReview(String id) {
		Map<Object, Object> responce = new HashMap<>();
		Optional<ProductReview> findById = this.productReviewRepo.findById(id);
		if (findById.isPresent()) {
			this.productReviewRepo.delete(findById.get());
		} else {
			throw new ResourceNotFoundException(AppConstant.PRODUCTREVIEW_NOT_FOUND, AppConstant.ID, id);

		}
		responce.put(AppConstant.MESSAGE, AppConstant.DELETE_SUCCESS);

		return new ResponseEntity<>(responce, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> updateProductReview(String id, Integer numberOfStar, String description, String productid,
			List<MultipartFile> image, String title) {

		Map<Object, Object> responce = new HashMap<>();
		Optional<ProductReview> findById = this.productReviewRepo.findById(id);
		if (findById.isPresent()) {

			ProductReview productReview = findById.get();
			productReview.setDescription(description);
			productReview.setNumberOfStar(numberOfStar);
			productReview.setTitle(title);
			productReview.setProduct(productReview.getProduct());
			productReview.setUser(productReview.getUser());
			if (!image.isEmpty()) {
				List<ReviewImage> image2 = productReview.getImage();
				for (MultipartFile file : image) {
					String uploadImage = appUtils.uploadImage(file, AppConstant.PRODUCT_IMAGE_PATH, null);
					ReviewImage r = new ReviewImage();
					r.setImageReview(productReview);
					r.setImageName(uploadImage);

					image2.add(r);

				}
				productReview.getImage().addAll(image2);

			}
			this.productReviewRepo.save(productReview);

		}
		responce.put(AppConstant.MESSAGE, AppConstant.UPDATE_PRODUCT_REVIEW);
		return new ResponseEntity<>(responce, HttpStatus.OK);
	}

}
