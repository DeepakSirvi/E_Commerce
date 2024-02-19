package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Complaint;
import com.ecommerce.model.ComplaintImage;
import com.ecommerce.repository.ComplaintImageRepo;
import com.ecommerce.repository.ComplaintRepo;
import com.ecommerce.service.ComplaintImageService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class ComplaintImageServiceImpl implements ComplaintImageService {

	@Autowired
	private ComplaintImageRepo complaintImageRepo;

	@Autowired
	private AppUtils appUtils;

	@Autowired
	private ComplaintRepo complaintRepo;

	@Override
	public Map<String, Object> createComplaintImage(String complaintId, MultipartFile file) {

		Map<String, Object> response = new HashMap<>();

		Complaint complaint = complaintRepo.findById(complaintId)

				.orElseThrow(() -> new ResourceNotFoundException(complaintId));

		if (file != null) {
			ComplaintImage complaintImage = new ComplaintImage();

			String uploadImage = appUtils.uploadImage(file, AppConstant.COMPLAINT_IMAGE_PATH, null);

			complaintImage.setImageName(uploadImage);

			complaintImage.setComplaint(complaint);

			complaintImageRepo.save(complaintImage);

			complaint.getImage().add(complaintImage);

			complaintRepo.save(complaint);

			response.put("response", AppConstant.COMPLAINTIMAGE_ADD_SUCCES);

		}

		return response;
	}

	@Override
	public Map<String, Object> updateComplaintImage(String complaintImageId, MultipartFile multipartFiles) {

		Map<String, Object> response = new HashMap<>();

		ComplaintImage complaintImage = complaintImageRepo.findById(complaintImageId)

				.orElseThrow(() -> new ResourceNotFoundException(complaintImageId));

		complaintImage.setImageName(uploadComplainImage(multipartFiles));
		complaintImageRepo.save(complaintImage);

		response.put("response", AppConstant.UPDATE_SUCCESFULLY);

		return response;

	}

	/*@Override
	public Map<String, Object> getComplaintImageById(String complaintImageId, MultipartFile multipartFiles) {

		Map<String, Object> response = new HashMap<>();

		ComplaintImage complaintImage = complaintImageRepo.findById(complaintImageId)

				.orElseThrow(() -> new ResourceNotFoundException(complaintImageId));

		if (multipartFiles != null) {

			String uploadImage = appUtils.uploadImage(multipartFiles, AppConstant.COMPLAINT_IMAGE_PATH, null);

			Optional<ComplaintImage> optionalComplaintImage = complaintImageRepo.findById(complaintImageId);

			complaintImage.setImageName(uploadImage);

		}

		return null;
	}*/

	public String uploadComplainImage(MultipartFile file) {
		if (file != null) {
			return appUtils.uploadImage(file, AppConstant.COMPLAINT_IMAGE_PATH, null);

		}
		return null;
	}

	@Override
	public Map<String, Object> removeComplaintImageById(String complaintImageId) {
		
		Map<String, Object> response = new HashMap<>();
		
		if (complaintImageRepo.existsById(complaintImageId)) {

			complaintImageRepo.deleteById(complaintImageId);
			
			response.put("response", AppConstant.REMOVE_FROM_COMPLAINT_IMAGE);

		} else {

			throw new ResourceNotFoundException(AppConstant.COMPLAINTIMAGE, AppConstant.ID, complaintImageId);
		}

		return response;
	

		
	
	}

}
