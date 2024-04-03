package com.gdu.myapp.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BbsDto {
	private int bbsNo, state, depth, groupNo, groupOrder;
	private String contents;
	private Date createDt;
	private UserDto user;
}
