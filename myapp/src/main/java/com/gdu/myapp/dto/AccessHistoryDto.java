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
public class AccessHistoryDto {
	private int accessHistoryNo;
	private String email, ip;
	private Date signinDt, signoutDt;
	
}
