<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>
 <jsp:include page="../layout/header.jsp">
   <jsp:param value="Sign In" name="title"/>
 </jsp:include>
 
   <h1 class="title">Sign In</h1>
  
<div>
  <form method="POST"
        action="${contextPath}/user/signin.do">
    <div>
      <label for="email">아이디</label>
      <input type="text" id="email" name="email" placeholder="example@naver.com">
    </div>
    <div>
      <label for="pw">비밀번호</label>
      <input type="password" id="pw" name="pw" placeholder="●●●●">
    </div>
    <div>
      <input type="hidden" name="url" value="${url}">
      <button type="submit">Sign In</button>
    </div>
    <div>
      <a href="${naverLoginURL}">
        <img src="${contextPath}/resources/img/btnG_아이콘원형.png">
      </a>
    </div>
  </form>
</div>
  
<%@ include file="../layout/footer.jsp" %>

