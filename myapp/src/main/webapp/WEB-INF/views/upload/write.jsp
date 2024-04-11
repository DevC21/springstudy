<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>

 <jsp:include page="../layout/header.jsp">
   <jsp:param value="업로드 작성화면" name="title"/>
 </jsp:include>
 
  <h1 class="title">업로드 작성화면</h1>

  <form id="frm-upload-register"
        method="POST"
        enctype="multipart/form-data"
        action="${contextPath}/upload/register.do">
  
    <div>
      <span>작성자</span>
      <span>${sessionScope.user.email}</span>
    </div>
  
    <div>
      <label for="title">제목</label>
      <input type="text" name="title" id="title">
    </div>
  
    <div>
      <textarea id="contents" name="contents" placeholder="내용을 입력하세요"></textarea>
    </div>
  
    <div>
      <label for="files">첨부</label>
      <input type="file" name="files" id="files" class="files" multiple>
    </div>
  
    <div id="attach-list"></div>
  
    <div>
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
      <button type="submit">작성완료</button>
      <a href="${contextPath}/upload/list.do"><button type="button">작성취소</button></a>
    </div>
  
  </form>

  <script>
    const fnFileCheck = ()=>{
      $('.files').on('change', (evt)=>{
        const limitPerSize = 1024 * 1024 * 10;
        const limitTotalSize = 1024 * 1024 * 100;
        let totalSize = 0;
        const files = evt.target.files;
        const fileList = document.getElementById('attach-list');
        fileList.innerHTML = '';
        for(let i = 0; i < files.length; i++){
          if(files[i].size > limitPerSize){
            alert('각 첨부 파일의 최대 크기는 10MB입니다.');
            evt.target.value = '';
            fileList.innerHTML = '';
            return;
          }
          totalSize += files[i].size;
          if(totalSize > limitTotalSize){
            alert('전체 첨부 파일의 최대 크기는 100MB입니다.');
            evt.target.value = '';
            fileList.innerHTML = '';
            return;
          }
          fileList.innerHTML += '<div>' + files[i].name + '</div>';
        }
      })
    }
    
    const fnRegisterUpload = (evt) => {
      if(document.getElementById('title').value == ''){
        alert('제목 입력은 필수입니다.');
        evt.preventDefault();
        return;
      }
    }
    
    document.getElementById('frm-upload-register').addEventListener('submit', (evt) => {
    	fnRegisterUpload(evt);
    })
  
    fnFileCheck();
  </script>
  
<%@ include file="../layout/footer.jsp" %>