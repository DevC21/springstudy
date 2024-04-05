package com.gdu.myapp.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.myapp.service.BlogService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/blog")
@RequiredArgsConstructor
@Controller
public class BlogController {

	private final BlogService blogSerivce;
	
	@PostMapping(value="/summernote/imageUpload.do", produces="application/json")
	public ResponseEntity<Map<String, Object>> summernoteImageUpload(@RequestParam("image") MultipartFile multipartFile){
		return blogSerivce.summernoteImageUpload(multipartFile);
	}
	
	@GetMapping("/list.do")
	public String list() {
		return "blog/list";
	}
	
	@GetMapping("/write.page")
	public String writePage() {
		return "blog/write";
	}
	
	
}
