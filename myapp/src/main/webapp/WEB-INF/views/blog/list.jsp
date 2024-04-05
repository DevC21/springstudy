<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>

 <jsp:include page="../layout/header.jsp">
   <jsp:param value="main" name="title"/>
 </jsp:include>
 
  <h1 class="title">블로그 목록</h1>
  
  <a href="${contextPath}/blog/write.page">블로그작성</a>
  
  
  
<%@ include file="../layout/footer.jsp" %>