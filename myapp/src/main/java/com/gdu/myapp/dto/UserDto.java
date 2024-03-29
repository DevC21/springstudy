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
public class UserDto {
	private int userNo, eventAgree, signupKind;
	private String email, pw, name, gender, mobile;
	private Date pwModifyDt, signupDt;
}
