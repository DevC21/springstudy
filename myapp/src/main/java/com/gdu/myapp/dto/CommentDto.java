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
public class CommentDto {
	private int commentNo, depth, groupNo, blogNo;
	private String contents;
	private Timestamp createDt;
	private UserDto user;
}
