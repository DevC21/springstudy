package com.gdu.myapp.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BlogDto {
	private int blogNo, hit;
	private String title, contents;
	private Timestamp createDt, modifyDt;
	private UserDto user;
}