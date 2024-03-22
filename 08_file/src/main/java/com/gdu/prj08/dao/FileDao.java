package com.gdu.prj08.dao;

import java.util.List;

import com.gdu.prj08.dto.FileDto;
import com.gdu.prj08.dto.HistoryDto;

public interface FileDao {
	int registerHistory(HistoryDto history);
  int registerFile(FileDto file);
  int modifyFile(FileDto file);
  int removeFile(int fileNo);
  List<FileDto> getFileList();
  FileDto getFileByNo(int fileNo);
}
