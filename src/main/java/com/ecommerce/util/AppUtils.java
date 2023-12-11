package com.ecommerce.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AppUtils {
	
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Value("${path}")
	private String path;
	
   
	public Integer generateOtp() {
		Random r = new Random();
		return r.nextInt(1000,9999);
	}
	
	public Long getUserId() {
		HttpServletRequest  httpRequest = RequestContextHolder.getRequest();
		if(httpRequest!=null)
		{
		   String token = httpRequest.getParameter("Authorization");
		   return jwtUtils.getUserIdFromToken(token);
		}
		else
		{
			   throw new BadRequestException(AppConstant.INVALID_REQUEST);
		}
	}
	
     public static final void validatePageAndSize(Integer page, Integer size) {
		
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size <= 0) {
			throw new BadRequestException("Size number cannot be less than zero.");
		}

		if (size > AppConstant.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstant.MAX_PAGE_SIZE);
		}
	}
     
     public String uploadImage(MultipartFile file,String dir) {
 		String currentDir = System.getProperty("user.dir") + path +File.separator+dir + File.separator;
 		System.out.println(currentDir);
 	    String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
 	    String	uuid = UUID.randomUUID().toString();
 		String randomName =  uuid.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
 		System.out.println(randomName);
 		try {
 			Files.copy(file.getInputStream(),Paths.get(currentDir , randomName), StandardCopyOption.REPLACE_EXISTING);
 		} catch (IOException e) {
 			//e.printStackTrace();
 		}
 		System.out.println(randomName);
 		return dir+File.separator+randomName;
 	}

	
}
