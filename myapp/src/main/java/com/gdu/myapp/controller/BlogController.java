package com.gdu.myapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.myapp.service.BlogService;

import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;

@RequestMapping("/blog")
@RequiredArgsConstructor
@Controller
public class BlogController {

	private final BlogService blogSerivce;
	
	@GetMapping("/list.page")
	public String list() {
		return "blog/list";
	}
	
	@GetMapping("/write.page")
	public String writePage() {
		return "blog/write";
	}
	
	@PostMapping(value="/summernote/imageUpload.do", produces="application/json")
	public ResponseEntity<Map<String, Object>> summernoteImageUpload(@RequestParam("image") MultipartFile multipartFile){
		return blogSerivce.summernoteImageUpload(multipartFile);
	}
	
	@PostMapping("/register.do")
	public String register(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("insertCount", blogSerivce.registerBlog(request));
		return "redirect:/blog/list.page";
	}
	
	@GetMapping(value="/getBlogList.do", produces="application/json")
	public ResponseEntity<Map<String, Object>> getBlogList(HttpServletRequest request) {
		return blogSerivce.getBlogList(request);
	}
	
	@GetMapping("/detail.do")
	public String detail(@RequestParam int blogNo, Model model) {
		model.addAttribute("blog", blogSerivce.getBlogByNo(blogNo));
		return "blog/detail";
	}
	
	@PostMapping(value="/registerComment.do", produces = "application/json")
	public ResponseEntity<Map<String, Object>> registerComment(HttpServletRequest request) {
		System.out.println(request.getParameter("contents"));
		System.out.println(request.getParameter("blogNo"));
		System.out.println(request.getParameter("userNo"));
		
		return new ResponseEntity<>(null);
	}
}
