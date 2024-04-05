package com.gdu.myapp.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gdu.myapp.mapper.UserMapper;

public class MyHttpSessionListener implements HttpSessionListener {

  @Override
  public void sessionCreated(HttpSessionEvent se) {

    // HttpSession
    HttpSession session = se.getSession();
    String sessionId = session.getId();
    
    System.out.println(sessionId + " 세션 정보가 생성되었습니다.");

  }
  
  // 세션 만료 시 자동으로 동작
  @Override
  public void sessionDestroyed(HttpSessionEvent se) {

    // HttpSession
    HttpSession session = se.getSession();
    
    // ApplicationContext
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
    
    // sesion_id
    String sessionId = session.getId();
    
    // getBean()
    // Service 를 추천하나, Mapper 도 가능
    UserMapper userMapper = ctx.getBean("userMapper", UserMapper.class);
    
    // updateAccessHistory()
    userMapper.updateAccessHistory(sessionId);

    System.out.println(sessionId + " 세션 정보가 소멸되었습니다.");

  }

}