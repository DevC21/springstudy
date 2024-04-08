package com.gdu.myapp.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.myapp.dto.BbsDto;
import com.gdu.myapp.dto.BlogDto;
import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.BlogMapper;
import com.gdu.myapp.utils.MyFileUtils;
import com.gdu.myapp.utils.MyPageUtils;
import com.gdu.myapp.utils.MySecurityUtils;

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
	public int registerBlog(HttpServletRequest request) {
		
    // 요청 파라미터
    String title = request.getParameter("title");
    String contents = request.getParameter("contents");
    int userNo = Integer.parseInt(request.getParameter("userNo"));

    // UserDto + BlogDto 객체 생성
    UserDto user = new UserDto();
    user.setUserNo(userNo);
    BlogDto blog = BlogDto.builder()
                     .title(MySecurityUtils.getPreventXss(title))
                     .contents(MySecurityUtils.getPreventXss(contents))
                     .user(user)
                   .build();

    /* summernote 편집기에서 사용한 이미지 확인하는 방법 (Jsoup 라이브러리) */
    Document document = Jsoup.parse(contents);
    Elements elements = document.getElementsByTag("img");
    if(elements != null) {
      for(Element element : elements) {
        String src = element.attr("src");
        /* src 정보를 DB에 저장하는 코드 등이 이 곳에 있으면 된다. */
        System.out.println(src);
      }
    }

    // DB에 blog 저장
    return blogMapper.insertBlog(blog);
    
	}
	
	@Override
	public ResponseEntity<Map<String, Object>> getBlogList(HttpServletRequest request) {
		
		// 전체 Blog 게시글 수
		int total = blogMapper.getBlogCount();
		
		// 스크롤 이벤트마다 가져갈 목록 개수
		int display = 10;
		
		// 현재 페이지 번호
		int page = Integer.parseInt(request.getParameter("page"));
		
		// 페이징 처리에 필요한 정보 처리
		myPageUtils.setPaging(total, display, page);
		
		// DB 로 보낼 Map 생성
		Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
																	  ,"end", myPageUtils.getEnd());
		
		// DB 에서 목록 가져오기
		List<BlogDto> blogList = blogMapper.getBlogList(map);
		
		return new ResponseEntity<Map<String,Object>>(Map.of("blogList", blogList,
																												 "totalPage", myPageUtils.getTotalPage())
																						  	, HttpStatus.OK);
	}

	@Override
	public BlogDto getBlogByNo(int blogNo) {
		
		return blogMapper.getBlogByNo(blogNo);
	}
	
}
