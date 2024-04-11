package com.gdu.myapp.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myapp.dto.UploadDto;

public interface UploadService {
	public boolean registerUpload(MultipartHttpServletRequest multipartRequest);
	public void loadUploadList(Model model);
	public UploadDto getUploadByNo(int uploadNo);
}
