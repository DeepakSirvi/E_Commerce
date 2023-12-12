package com.ecommerce.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.util.AppUtils;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/ecommerce/image")
public class GetImageController {
	
	@Autowired
	private AppUtils appUtils;

	@RequestMapping(value = "/{folderName}/{fileName}", method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
	public void getImage(@PathVariable("folderName") String folderName,@PathVariable("fileName") String fileName,HttpServletResponse response) throws IOException {
		InputStream data = appUtils.getImages(folderName+"\\"+fileName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(data, response.getOutputStream());
	}
}
