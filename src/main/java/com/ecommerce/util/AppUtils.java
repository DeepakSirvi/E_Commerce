package com.ecommerce.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AppUtils {
	
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Value("${app.path}")
	private String path;
	
	private static final String GLOBAL_DIR = System.getProperty("user.dir");
   
	public Integer generateOtp() {
		Random r = new Random();
		return r.nextInt(1000,9999);
	}
	
	public String getUserId() {
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
     
     public String uploadImage(MultipartFile file,String dir,String uuid) {
 		String currentDir = GLOBAL_DIR + path + File.separator + dir + File.separator;
 	    String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
 	    if(!Objects.nonNull(uuid))
 	   	uuid = UUID.randomUUID().toString();
 		String randomName =  uuid.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
 		System.out.println(currentDir +randomName);
 		try {
 			Files.copy(file.getInputStream(),Paths.get(currentDir , randomName), StandardCopyOption.REPLACE_EXISTING);
 		} catch (IOException e) {
 			//e.printStackTrace();
 		}
 		return dir+File.separator+randomName;
 	}
     
     public InputStream getImages(String fileName){
 	 	try {
 			return new FileInputStream(GLOBAL_DIR + path + File.separator+fileName);
 	} catch (FileNotFoundException e) {
 		e.printStackTrace();
 	}
 	return null;
 }

	
}
