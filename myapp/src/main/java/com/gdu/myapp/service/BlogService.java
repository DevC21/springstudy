package com.gdu.myapp.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface BlogService {
	ResponseEntity<Map<String, Object>> summernoteImageUpload(MultipartFile multipartFile);
	int registerBlog();
}
