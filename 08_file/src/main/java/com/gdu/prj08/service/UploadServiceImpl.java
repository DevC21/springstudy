package com.gdu.prj08.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.prj08.dao.FileDao;
import com.gdu.prj08.dto.FileDto;
import com.gdu.prj08.dto.HistoryDto;
import com.gdu.prj08.utils.MyFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

	private final FileDao fileDao;
	private final MyFileUtils myFileUtils;
	
  @Override
  public int upload1(MultipartHttpServletRequest multipartRequest) {

    // 첨부 파일 작성자
    String writer = multipartRequest.getParameter("writer");
    
    // 첨부 파일 작성자 IP
    String IP = multipartRequest.getRemoteAddr();
    
    // 등록할 HistoryDto 생성
    HistoryDto historyDto = HistoryDto.builder()
    																.writer(writer)
    																.IP(IP)
    															.build();
    
    // 등록
    fileDao.registerHistory(historyDto);
    
    // 첨부 파일 목록
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
  	int insertCount = 0;
    
    // 첨부 파일 목록 순회
    for(MultipartFile multipartFile : files) {

      // 첨부 파일 존재 여부 확인
      if(multipartFile != null && !multipartFile.isEmpty()) {

        // 첨부 파일 경로
        String uploadPath = myFileUtils.getUploadPath();

        // 첨부 파일 경로 디렉터리 만들기
        File dir = new File(uploadPath);
        if(!dir.exists()) {
          dir.mkdirs();
        }

        // 첨부 파일 원래 이름
        String originalFilename = multipartFile.getOriginalFilename();

        // 첨부 파일 저장 이름
        String filesystemName = myFileUtils.getFilesystemName(originalFilename);

        // 첨부 파일 File 객체
        File file = new File(dir, filesystemName);
        
        // 저장
        try {          
          multipartFile.transferTo(file);
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        // 등록할 FileDto 생성
        FileDto fileDto = FileDto.builder()
            .uploadPath(uploadPath)
            .originalFilename(originalFilename)
            .filesystemName(filesystemName)
            .historyNo(historyDto.getHistoryNo())
          .build();
        
        // 등록
        if(fileDao.registerFile(fileDto) >= 1)
        	insertCount++;
      }
    }

    if(insertCount >= 1)
    	return 1;
    else
    	return 0;
  }

  @Override
  public Map<String, Integer> upload2(MultipartHttpServletRequest multipartRequest) {
  	
    // 첨부 파일 목록
    List<MultipartFile> files = multipartRequest.getFiles("files");

    // 첨부 파일 목록 순회
    for(MultipartFile multipartFile : files) {

      // 첨부 파일 존재 여부 확인
      if(multipartFile != null && !multipartFile.isEmpty()) {

        // 첨부 파일 경로
        String uploadPath = myFileUtils.getUploadPath();

        // 첨부 파일 경로 디렉터리 만들기
        File dir = new File(uploadPath);
        if(!dir.exists()) {
          dir.mkdirs();
        }

        // 첨부 파일 원래 이름
        String originalFilename = multipartFile.getOriginalFilename();

        // 첨부 파일 저장 이름
        String filesystemName = myFileUtils.getFilesystemName(originalFilename);

        // 첨부 파일 File 객체
        File file = new File(dir, filesystemName);

        // 저장
        try {          
          multipartFile.transferTo(file);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    return Map.of("success", 1);
  }
}
