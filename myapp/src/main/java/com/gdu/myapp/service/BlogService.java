package com.gdu.myapp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.myapp.dto.BlogDto;

public interface BlogService {
	ResponseEntity<Map<String, Object>> summernoteImageUpload(MultipartFile multipartFile);
	int registerBlog(HttpServletRequest request);
	ResponseEntity<Map<String, Object>> getBlogList(HttpServletRequest request);
	BlogDto getBlogByNo(int blogNo);
}
