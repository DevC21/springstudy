package com.gdu.myapp.service;

import java.io.File;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.myapp.mapper.BlogMapper;
import com.gdu.myapp.utils.MyFileUtils;
import com.gdu.myapp.utils.MyPageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

	private final BlogMapper blogMapper;
	private final MyPageUtils myPageUtils;
	private final MyFileUtils myFileUtils;
	
	@Override
	public ResponseEntity<Map<String, Object>> summernoteImageUpload(MultipartFile multipartFile) {
		
		// 이미지 저장할 경로 생성
		String uploadPath = myFileUtils.getUploadPath();
		File dir = new File(uploadPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		// 이미지 저장할 이름 생성
		String filesystemName = myFileUtils.getFilesystemName(multipartFile.getOriginalFilename());
		
		
		// 실제 저장
		File file = new File(dir, filesystemName);
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// 이미지가 저장된 경로를 Map 으로 반환
		// view 단으로 보낼 src = "/myapp/upload/2024/03/27/1234567890.jpg"
		// servlet-context.xml 에서 <resources> 태그를 추가한다. 
		// <resources mapping="/upload/**" location="file:///upload/" />
		Map<String, Object> map = Map.of("src", uploadPath + "/" + filesystemName);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@Override
	public int registerBlog() {
		// TODO Auto-generated method stub
		return 0;
	}

}
