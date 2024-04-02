package com.gdu.myapp.mapper;

import java.util.Map;

import com.gdu.myapp.dto.LeaveUserDto;
import com.gdu.myapp.dto.UserDto;

public interface UserMapper {
  UserDto getUserByMap(Map<String, Object> map);
	int insertUser(UserDto user);
	LeaveUserDto getLeaveUserBymap(Map<String, Object> map);
	int insertAccessHistory(Map<String, Object> map);
	int deleteUser(int userNo);
}
