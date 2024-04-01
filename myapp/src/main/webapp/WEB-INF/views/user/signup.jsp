<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- include libraries(jquery, bootstrap) -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link rel="stylesheet" href="${contextPath}/resources/summernote-0.8.18-dist/summernote.min.css">
<script src="${contextPath}/resources/summernote-0.8.18-dist/summernote.min.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/lang/summernote-ko-KR.min.js"></script>


<style>
  @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css')
  @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap')
  * {
      font-family: "Noto Sans KR", sans-serif;
      font-weight: 400;
  }
</style>


</head>
<body>

  <h1>Sign Up</h1>
  
  <form method="post"
        action="${contextPath}/user/signup.do"
        id="frm-signup">
  
    <div class="mb-3">
      <label for="email">아이디</label>
      <input type="text" id="email" name="email" placeholder="example@example.com">
      <button type="button" id="btn-code" class="btn btn-primary">인증코드받기 </button>
      <div id="msg-email"></div>
    </div>
    <div class="mb-3">
      <input type="text" id="code" name="code" placeholder="인증코드입력" disabled>
      <button type="button" id="btn-verify-code" class="btn btn-primary">인증하기</button>
    </div>
  
  </form>
  
  <script>
  
/*
  new Promise((resolve, reject) => {
    $.ajax({
      url: '이메일중복체크요청'
    })
    .done(resData => {
      if(resData.enableEmail){
        resolve();
      } else {
        reject();
      }
    })
  })
  .then(() => {
    $.ajax({
      url: '인증코드전송요청'
    })
    .done(resData => {
      if(resData.code === 인증코드입력값)
    })
  })
  .catch(() => {
    
  })
*/

/*
  fetch('이메일중복체크요청', {})
  .then(response=>response.json())
  .then(resData=>{
    if(resData.enableEmail){
      fetch('인증코드전송요청', {})
      .then(response=>response.json())
      .then(resData=>{  // {"code": "123asb"}
        if(resData.code === 인증코드입력값)
      })
    }
  })
*/
  
  let email = document.getElementById('email');
  let regEmail = /^[A-Za-z0-9-_]{2,}@[A-Za-z0-9]+(\.[A-Za-z]{2,6}){1,2}$/;
  if(!regEmail.test(email.value)){
    alert('이메일 형식이 올바르지 않습니다.');
    return;
  }
  
  const fnGetContextPath = ()=>{
	  const host = location.host; /* localhost:8080 */
	  const url = location.href   /* http://localhost:8080/mvc/getDate.do */
	  const begin = url.indexOf(host) + host.length;
	  const end = url.indexOf('/', begin + 1);
	  return url.substring(begin, end);
	}
  
  const fnCheckEmail = ()=>{
	  fetch(fnGetContextPath() + '/user/checkEmail.do', {
	    method: 'POST',
	    headers: {
	      'Content-Type': 'application/json'
	    },
	    body: JSON.stringify({
	      'email': email.value
	    })
	  })
	  .then(response=>response.json())
	  .then(resData=> {
		  if(resData.enableEmail){
			  fetch(fnGetContextPath() + '/user/sendCode.do', {
			      method: 'POST',
			      headers: {
			        'Content-Type': 'application/json'
			      },
			      body: JSON.stringify({
			        'email': email.value
			      })
			  })
			  .then(response=>response.json())
        .then(resData=> {
        	if(resData.code === )
        })
		  } else {
			  document.getElementById('msg-email').innerHTML = '이미 사용 중인 이메일입니다.';
			  return;
		  }
	  })
  }
  
  document.getElementById('btn-code').addEventListener('click', fnCheckEmail);
  
  
  </script>
</body>
</html>