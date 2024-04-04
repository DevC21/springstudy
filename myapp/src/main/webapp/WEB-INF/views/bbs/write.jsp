<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>

 <jsp:include page="../layout/header.jsp">
   <jsp:param value="main" name="title"/>
 </jsp:include>
 
  <h1 class="title">BBS 작성화면</h1>
  
  <form id="frm-bbs-register"
        method="POST"
        action="${contextPath}/bbs/register.do">
    <div>
      <span>작성자</span>
      <span>${sessionScope.user.email}</span>
    </div>
    
    <div>
      <textarea id="contents" name="contents" placeholder="내용을 입력하세요."></textarea>
    </div>
    
    <div>
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
      <button type="submit">작성완료</button>
      <a href="${contextPath}/bbs/list.do"><button type="button">작성취소</button></a>
    </div>
  </form>
  
  <script>
  
  const fnRegisterBbs = (evt) => {
	  if(document.getElementById('contents').value == ''){
		  alert('내용 입력은 필수입니다.');
		  evt.preventDefault();
		  return;
	  }
  }
  
  document.getElementById('frm-bbs-register').addEventListener('submit', (evt) => {
	  fnRegisterBbs(evt);
	})
  
  
  </script>
  
<%@ include file="../layout/footer.jsp" %>