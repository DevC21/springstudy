package com.gdu.prj08.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UploadService {
	int upload1(MultipartHttpServletRequest multipartRequest);
	Map<String, Integer> upload2(MultipartHttpServletRequest multipartRequest);
}
