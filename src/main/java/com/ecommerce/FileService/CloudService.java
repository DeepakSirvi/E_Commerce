package com.ecommerce.FileService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
	
		public String uploadFileInFolder(MultipartFile file, String destinationPath);

		public String uploadFileInFolde(MultipartFile file, String destinationPath);
		
		public List<String> uploadFileInFolder(List<MultipartFile> file, String destinationPath);

		public InputStream getImages(String fileName, String destination);
		
		 public void  deleteImageFromCloudServer(String imageUrl) throws IOException ;

	}

