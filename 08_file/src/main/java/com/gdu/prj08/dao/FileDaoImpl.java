package com.gdu.prj08.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.gdu.prj08.dto.FileDto;
import com.gdu.prj08.dto.HistoryDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileDaoImpl implements FileDao {

  private final SqlSessionTemplate sqlSessionTemplate;

  public final static String NS = "com.gdu.prj08.mybatis.mapper.file_t."; 
  
  @Override
  public int registerHistory(HistoryDto history) {
  	return sqlSessionTemplate.insert(NS + "registerHistory", history);
  }
  
  @Override
  public int registerFile(FileDto file) {
    return sqlSessionTemplate.insert(NS + "registerFile", file);
  }

  @Override
  public int modifyFile(FileDto file) {
    int updateCount = sqlSessionTemplate.update(NS + "modifyFile", file);
    return updateCount;
  }

  @Override
  public int removeFile(int fileNo) {
    int deleteCount = sqlSessionTemplate.delete(NS + "removeFile", fileNo);
    return deleteCount;
  }

  @Override
  public List<FileDto> getFileList() {
    List<FileDto> fileList = sqlSessionTemplate.selectList(NS + "getFileList");
    return fileList;
  }

  @Override
  public FileDto getFileByNo(int fileNo) {
  	FileDto file = sqlSessionTemplate.selectOne(NS + "getFileByNo", fileNo);
    return file;
  }

}
