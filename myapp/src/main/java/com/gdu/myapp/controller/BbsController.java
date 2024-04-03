package com.gdu.myapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.myapp.service.BbsService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/bbs")
@RequiredArgsConstructor
@Controller
public class BbsController {

	private final BbsService bbsService;
	
	@GetMapping("/list.do")
	public String list(HttpServletRequest request, Model model) {
		bbsService.loadBbsList(request, model);
		
		return "bbs/list";
	}
	
	@GetMapping("/write.page")
	public String writePAge() {
		return "bbs/write";
	}
	
}
