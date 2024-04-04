package com.gdu.myapp.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class RequiredSignoutInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// preHandle() 메소드 반환값
		// 1. True : 요청을 처리한다.
		// 2. False : 요청을 처리하지 않는다.
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") != null) {
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.println("<script>");
	    out.println("alert('해당 기능은 사용할 수 없습니다.')");
	    out.println("history.back()");
	    out.println("</script>");
	    out.flush();
	    out.close();
	    return false; // 컨트롤러로 요청이 전달되지 않는다.
		}
		
		return true;
	}
	

}
