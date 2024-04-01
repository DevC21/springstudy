package com.gdu.myapp.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.gdu.myapp.dto.UserDto;

public class SignoutListener implements HttpSessionListener {

  @Override
  public void sessionCreated(HttpSessionEvent se) {
    System.out.println("세션 생성");
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {

    HttpSession session = se.getSession();

    UserDto user = (UserDto) session.getAttribute("user");

    if(user == null) {
      System.out.println("세션 정보 없음");
    } else {
      System.out.println(user.getEmail());
    }

  }

}