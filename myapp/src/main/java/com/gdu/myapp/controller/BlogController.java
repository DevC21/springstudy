package com.gdu.myapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
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
	
	@GetMapping("/updateHit.do")
	public String updateHit(@RequestParam int blogNo) {
		blogSerivce.updateHit(blogNo);
		return "redirect:/blog/detail.do?blogNo=" + blogNo;
	}
	
	@GetMapping("/detail.do")
	public String detail(@RequestParam int blogNo, Model model) {
		model.addAttribute("blog", blogSerivce.getBlogByNo(blogNo));
		return "blog/detail";
	}
	
	@PostMapping(value="/registerComment.do", produces="application/json")
	public ResponseEntity<Map<String, Object>> registerComment(HttpServletRequest request) {
		return new ResponseEntity<>(Map.of("insertCount", blogSerivce.registerComment(request))
														  , HttpStatus.OK);
	}
	
	@GetMapping(value="/comment/list.do", produces="application/json")
	public ResponseEntity<Map<String, Object>> commentList(HttpServletRequest request) {
		/*
		 * return new ResponseEntity<>(blogSerivce.getCommentList(request) ,
		 * HttpStatus.OK);
		 */
		return ResponseEntity.ok(blogSerivce.getCommentList(request));
	}
	
	@PostMapping(value="/registerReply.do", produces="application/json")
	public ResponseEntity<Map<String, Object>> registerReply(HttpServletRequest request) {
		return new ResponseEntity<>(Map.of("insertCount", blogSerivce.registerReply(request))
														  , HttpStatus.OK);
	}
	
	@GetMapping("/removeComment.do")
	public String removeComment(@RequestParam("commentNo") int commentNo,
															@RequestParam("blogNo") int blogNo) {
		blogSerivce.removeComment(commentNo);
		return "redirect:/blog/detail.do?blogNo=" + blogNo;
	}
	
}
