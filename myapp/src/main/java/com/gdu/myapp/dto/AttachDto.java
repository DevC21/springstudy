package com.gdu.myapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AttachDto {
	private int attachNo, downloadCount, hasThumbnail, uploadNo;
	private String uploadPath, filesystemName, originalFilename;
}
