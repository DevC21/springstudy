package com.gdu.prj07.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		// preHandle() 메소드 반환값
		// 1. True : 요청을 처리한다.
		// 2. False : 요청을 처리하지 않는다.
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") == null) {
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.println("<script>");
	    out.println("alert('로그인을 해주세요.')");
	    out.println("history.back()");
	    out.println("</script>");
	    return false; // 컨트롤러로 요청이 전달되지 않는다.
		}
		
		return true;
	}
}
	