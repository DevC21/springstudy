package com.gdu.myapp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

public interface UserService {
	
	// 가입 및 탈퇴
	ResponseEntity<Map<String, Object>> checkEmail(Map<String, Object> params);
	ResponseEntity<Map<String, Object>> sendCode(Map<String, Object> params);
	void signup(HttpServletRequest request, HttpServletResponse response);
	void leave(HttpServletRequest request, HttpServletResponse response);
	
	// 로그인 및 로그아웃
	String getRedirectURLAfterSignin(HttpServletRequest request);
	String getNaverLoginURL(HttpServletRequest request);
	void signin(HttpServletRequest request, HttpServletResponse response);
	void signout(HttpServletRequest request, HttpServletResponse response);
}
