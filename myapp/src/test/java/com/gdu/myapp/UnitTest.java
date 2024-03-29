package com.gdu.myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UserMapper;

/* 1. JUnit5 를 이용한다. */
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})

public class UnitTest {
  
	@Autowired
  private UserMapper userMapper;
  
  @Test
  public void test01_등록() {
    UserDto user = UserDto.builder().build();
    int insertCount = userMapper.insertUser(user);
    assertEquals(1, insertCount);
  }

  
}
