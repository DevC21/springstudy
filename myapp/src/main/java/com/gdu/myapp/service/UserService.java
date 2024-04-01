package com.gdu.myapp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

public interface UserService {
	void signin(HttpServletRequest request, HttpServletResponse response);
	public ResponseEntity<Map<String, Object>> checkEmail(Map<String, Object> params);
	void signout(HttpServletRequest request, HttpServletResponse response);
	void signup(HttpServletRequest request, HttpServletResponse response);
	void leave(HttpServletRequest request, HttpServletResponse response);
}
