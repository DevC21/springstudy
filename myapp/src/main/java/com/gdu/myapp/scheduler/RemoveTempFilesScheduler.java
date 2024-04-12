package com.gdu.myapp.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.myapp.service.UploadService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RemoveTempFilesScheduler {
	
	private final UploadService uploadService;

	// 12시 20분에 removeTempFiles 서비스가 동작하는 스케쥴러
  @Scheduled(cron="0 20 12 * * ?")
	public void execute() {
  	uploadService.removeTempFiles();
  	System.out.println("임시 파일을 삭제 했습니다.");
	}
}
