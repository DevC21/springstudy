<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>
 <jsp:include page="../layout/header.jsp">
   <jsp:param value="Sign In" name="title"/>
 </jsp:include>
 
<div class="m-3">

  <h1 class="title">Sign Up</h1>
  
  <form method="POST"
        action="${contextPath}/user/signup.do"
        id="frm-signup">
  
    <div class="row mb-3">
      <label for="inp-email" class="col-sm-3 col-form-label">아이디</label>
      <div class="col-sm-6"><input type="text" id="inp-email" name="email" class="form-control" placeholder="example@example.com"></div>
      <div class="col-sm-3"><button type="button" id="btn-code" class="btn btn-primary">인증코드받기</button></div>
      <div class="col-sm-3"id="msg-email"></div>
    </div>
    <div class="row mb-3">
      <label for="inp-code" class="col-sm-3 col-form-label">인증코드</label>
      <div class="col-sm-6"><input type="text" id="inp-code" class="form-control" placeholder="인증코드입력" disabled></div>
      <div class="col-sm-3"><button type="button" id="btn-verify-code" class="btn btn-primary" disabled>인증하기</button></div>
    </div>
    
    <hr class="my-3">
  
    <div class="row mb-3">
      <label for="inp-pw" class="col-sm-3 col-form-label">비밀번호</label>
      <div class="col-sm-6"><input type="password" id="inp-pw" name="pw" class="form-control"></div>
      <div class="col-sm-3"></div>
      <div class="col-sm-9" id="msg-pw"></div>
    </div>
    <div class="row mb-3">
      <label for="inp-pw2" class="col-sm-3 col-form-label">비밀번호 확인</label>
      <div class="col-sm-6"><input type="password" id="inp-pw2" class="form-control"></div>
      <div class="col-sm-3"></div>
      <div class="col-sm-9" id="msg-pw2"></div>
    </div>
    
    <hr class="my-3">
    
    <div class="row mb-3">
      <label for="inp-name" class="col-sm-3 col-form-label">이름</label>
      <div class="col-sm-9"><input type="text" name="name" id="inp-name" class="form-control"></div>
      <div class="col-sm-3"></div>
      <div class="col-sm-9" id="msg-name"></div>
    </div>

    <div class="row mb-3">
      <label for="inp-mobile" class="col-sm-3 col-form-label">휴대전화번호</label>
      <div class="col-sm-9"><input type="text" name="mobile" id="inp-mobile" class="form-control"></div>
      <div class="col-sm-3"></div>
      <div class="col-sm-9" id="msg-mobile"></div>
    </div>

    <div class="row mb-3">
      <label class="col-sm-3 form-label">성별</label>
      <div class="col-sm-3">
        <input type="radio" name="gender" value="none" id="rdo-none" class="form-check-input" checked>
        <label class="form-check-label" for="rdo-none">선택안함</label>
      </div>
      <div class="col-sm-3">
        <input type="radio" name="gender" value="man" id="rdo-man" class="form-check-input">
        <label class="form-check-label" for="rdo-man">남자</label>
      </div>
      <div class="col-sm-3">
        <input type="radio" name="gender" value="woman" id="rdo-woman" class="form-check-input">
        <label class="form-check-label" for="rdo-woman">여자</label>
      </div>
    </div>
    
    <hr class="my-3">
    
    <div class="form-check mb-3">
      <input type="checkbox" class="form-check-input" id="chk-service">
      <label class="form-check-label" for="chk-service">서비스 이용약관 동의(필수)</label>
    </div>
    <div>
      <textarea rows="5" class="form-control">본 약관은 ...</textarea>
    </div>
    
    <div class="form-check mb-3">
      <input type="checkbox" name="event" class="form-check-input" id="chk-event">
      <label class="form-check-label" for="chk-event">
        이벤트 알림 동의(선택)
      </label>
    </div>
    <div>
      <textarea rows="5" class="form-control">본 약관은 ...</textarea>
    </div>
    
    <hr class="my-3">
    
    <div class="m-3">
      <button type="submit" id="btn-signup" class="btn btn-primary">가입하기</button>
    </div>
    
  </form>

</div>

<script src="${contextPath}/resources/js/signup.js?dt=${dt}"></script>
  
<%@ include file="../layout/footer.jsp" %>
