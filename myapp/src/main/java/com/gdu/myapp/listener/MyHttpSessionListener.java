package com.gdu.myapp.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.gdu.myapp.mapper.UserMapper;

public class MyHttpSessionListener implements HttpSessionListener {

  @Autowired
  private UserMapper userMapper;

  @Override
  public void sessionCreated(HttpSessionEvent se) {

    HttpSession session = se.getSession();
    String sessionId = session.getId();

    System.out.println(sessionId + " 세션 정보가 생성되었습니다.");

  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {

    // Sign Out 기록 남기기
    HttpSession session = se.getSession();
    String sessionId = session.getId();
    userMapper.updateAccessHistory(sessionId);

    System.out.println(sessionId + " 세션 정보가 소멸되었습니다.");

  }

}