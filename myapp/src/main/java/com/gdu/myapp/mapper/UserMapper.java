package com.gdu.myapp.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myapp.dto.LeaveUserDto;
import com.gdu.myapp.dto.UserDto;

@Mapper
public interface UserMapper {
  UserDto getUserByMap(Map<String, Object> map);
	int insertUser(UserDto user);
	LeaveUserDto getLeaveUserBymap(Map<String, Object> map);
	int deleteUser(int userNo);
	
  int insertAccessHistory(Map<String, Object> map);
  int updateAccessHistory(String sessionId);
}
