<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>

 <jsp:include page="../layout/header.jsp">
   <jsp:param value="${upload.uploadNo}번 업로드" name="title"/>
 </jsp:include>
 
 <style>
 

  .blind {
    display: none;
  }
 
  .upload-detail {
    
    border: 1px solid gray;
  
  }
  
 </style>
 
  <h1 class="title">업로드 상세화면</h1>
  
  <a href="${contextPath}/upload/list.do">업로드 리스트</a>
  
  <div class="upload-detail">
    <div>
      <span>작성자</span>
      <span>${upload.user.email}</span>
    </div>
    
    <div>
      <span>제목</span>
      <span>${upload.title}</span>
    </div>
    
    <div>
      <span>내용</span>
      <span>${upload.contents}</span>
    </div>
    
    <div>
      <span>작성일자</span>
      <span>${upload.createDt}</span>
    </div>
    
    <div>
      <span>최종수정일</span>
      <span>${upload.modifyDt}</span>
    </div>
    
    <c:if test="${not empty sessionScope.user}">  
      <c:if test="${sessionScope.user.userNo == upload.user.userNo}">
        <form id="frm-btn" method="POST">  
          <input type="hidden" name="uploadNo" value="${upload.uploadNo}">
          <button type="button" id="btn-edit" class="btn btn-warning btn-sm">편집</button>
          <button type="button" id="btn-remove" class="btn btn-danger btn-sm">삭제</button>
        </form>
      </c:if>
    </c:if>
  </div>
  
  <hr>
  
  <!-- 첨부 목록 -->
  <h3>첨부 파일 다운로드</h3>
  <div>
    <c:if test="${empty attachList}">
      <div>첨부 없음</div>
    </c:if>
    <c:if test="${not empty attachList}">
      <c:forEach items="${attachList}" var="attach">
        <div class="attach" data-attach-no="${attach.attachNo}">
          <c:if test="${attach.hasThumbnail == 1}">
            <img src="${contextPath}${attach.uploadPath}/s_${attach.filesystemName}">
          </c:if>
          <c:if test="${attach.hasThumbnail == 0}">
            <img src="${contextPath}/resources/img/attach.png" width="96px">
          </c:if>
          
          <a>${attach.originalFilename}</a>
        </div>
      </c:forEach>
      <div>
        <a id="download-all" href="${contextPath}/upload/downloadAll.do?uploadNo=${upload.uploadNo}">모두 다운로드</a>
      </div>
    </c:if>
  </div>
  
  
  <script>
  const fnDownload = () => {
	  $('.attach').on('click', (evt) => {
	    if(confirm('해당 첨부 파일을 다운로드 할까요?')) {
	      location.href = '${contextPath}/upload/download.do?attachNo=' + evt.currentTarget.dataset.attachNo;
	    }
	  })
	}
  
	const fnDownloadAll = () => {
	  document.getElementById('download-all').addEventListener('click', (evt) => {
	    if(!confirm('모두 다운로드 할까요?')) {
	      evt.preventDefault();
	      return;
	    }
	  })
	}
  
	// 전역 객체
	var frmBtn = document.getElementById('frm-btn');
	
	const fnEditUpload = () => {
		document.getElementById('btn-edit').addEventListener('click', (evt) => {
		  frmBtn.action = '${contextPath}/upload/edit.do';
		  frmBtn.submit();
		})
		
	}
	
	const fnAfterModifyUpdate = () => {
		const updateCount = '${updateCount}';
		if(updateCount !== '') {
			if(updateCount === '1') {
				alert('게시글이 수정되었습니다.');
			} else {
				alert('게시글이 수정되지 않았습니다.');
			}
		}
	}
	
	
  fnDownload();
  fnDownloadAll();
  fnEditUpload();
  fnAfterModifyUpdate();
  </script>
  
<%@ include file="../layout/footer.jsp" %>