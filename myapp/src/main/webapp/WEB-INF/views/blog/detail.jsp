<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>

 <jsp:include page="../layout/header.jsp">
   <jsp:param value="${blog.blogNo}번 블로그" name="title"/>
 </jsp:include>
 
 <style>
 

  .blind {
    display: none;
  }
 
  .blog-detail {
    
    border: 1px solid gray;
  
  }
  
 </style>
 
  <h1 class="title">블로그 상세화면</h1>
  
  <a href="${contextPath}/blog/list.page">블로그 리스트</a>
  
  <div class="blog-detail">
    <div>
      <span>작성자</span>
      <span>${blog.user.email}</span>
    </div>
    
    <div>
      <span>조회수</span>
      <span>${blog.hit}</span>
    </div>
    
    <div>
      <span>제목</span>
      <span>${blog.title}</span>
    </div>
    
    <div>
      <span>내용</span>
      <span>${blog.contents}</span>
    </div>
    <c:if test="${sessionScope.user.userNo == blog.user.userNo}">
      <div>
        <button class="btn btn-success btn-blog-modify" type="button" data-blog-no="${blog.blogNo}">수정</button>
        <button class="btn btn-danger btn-blog-remove" type="button" data-blog-no="${blog.blogNo}">삭제</button>
      </div>
    </c:if>
  </div>
  
  <hr>
  
  <form id="frm-comment">
    <textarea id="contents" name="contents"></textarea>
    <input type="hidden" name="blogNo" value="${blog.blogNo}">
    <c:if test="${not empty sessionScope.user}">
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
    </c:if>
    <button id="btn-comment-register" type="button">댓글 등록</button>
  </form>

  <hr>
  
  <div id="comment-list"></div>
  <div id="paging"></div>
  
  <script>
  
    const fnCheckSignin = () => {
      if('${sessionScope.user}' === '') {
        if(confirm('Sign In 이 필요한 기능입니다. Sign In 할까요?')) {
          location.href = '${contextPath}/user/signin.page';
        }
      }
    }
    
    const fnRegisterComment = () => {
      $('#btn-comment-register').on('click', (evt) => {
    	  if('${sessionScope.user}' === ''){
    		  fnCheckSignin();  
    	  }
    	  else{
      	  $.ajax({
      		  // 요청
      		  type: 'POST',
      		  url: '${contextPath}/blog/registerComment.do',
      		  // <form> 내부의 모든 입력을 파라미터 형식으로 보낼 때 사용
      		  // 입력 요소들은 name 속성을 가지고 있어야 함
      		  data: $('#frm-comment').serialize(),
      		  // 응답
      		  dataType: 'json',
      		  success: (resData) => { // resData => {"insertCount": 1}
      			  if(resData.insertCount === 1){
      				  alert('댓글이 등록되었습니다.');
      				  $('#contents').val('');
      				  // 댓글 목록 보여주는 함수
      				  fnCommentList();
      			  } else {
      				  alert('댓글 등록이 실패했습니다');
      			  }
      		  },
      		  error: (jqXHR) => {
      			  alert(jqXHR.statusText + '(' + jqXHR.status + ')');
      		  }
      	  })
    	  }
      })
    }
    
    // 전역 변수
    
    var page = 1;
    
    const fnCommentList = () => {
    	$.ajax({
    		type: 'GET',
    		url: '${contextPath}/blog/comment/list.do',
    		data: 'blogNo=${blog.blogNo}&page=' + page,
    		dataType: 'json',
    		success: (resData) => { // resData = {"commentList": [], "paging": "< 1 2 3 4 5 >"}
    			let commentList = $('#comment-list');
    			let paging = $('#paging');
    			commentList.empty();
    			paging.empty();
    			if(resData.commentList.length === 0){
    				commentList.append('<div>댓글이 없습니다.</div>');
    				paging.empty();
    				return;
    			}
    			$.each(resData.commentList, (i, comment) => {
    				let str = '';
    				// 댓글 들여쓰기 (댓글 여는 <div>)
    				if(comment.depth === 0) {
    					str += '<div>';
    				} else {
    					str += '<div style="padding-left: 32px;">';
    				}
    				// 댓글 내용 표시
    				console.log(comment.state);
    				if(comment.state === 1){
      				str += '<span>';
      				str += comment.user.email;
      				str += '(' + moment(comment.createDt).format('YYYY.MM.DD.') + ')';
      				str += '</span>';
      				str += '<div>' + comment.contents + '</div>';
      				// 답글 버튼
      				if(comment.depth === 0) {
      					str += '<button type="button" class="btn btn-success btn-reply">답글</button>'
      				}
      				/* 답글 입력 화면 */
      				str += '<div class="blind">';
      				str += '  <form class="frm-reply">';
      				str += '    <input type="hidden" name="groupNo" value="' + comment.groupNo + '">';
      				str += '    <input type="hidden" name="blogNo" value="${blog.blogNo}">';
      				str += '    <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">';
      				str += '    <textarea name="contents" placeholder="답글 입력"></textarea>';
      				str += '    <button type="button" class="btn btn-warning btn-register-reply">작성완료</button>';
      				str += '  </form>';
      				str += '</div>';
      				/*                */
      				// 삭제 버튼 (내가 작성한 댓글에만 삭제 버튼이 생성됨)
      				if(Number('${sessionScope.user.userNo}') === comment.user.userNo) {
      					str += '<button type="button" class="btn btn-danger btn-remove" data-comment-no="' + comment.commentNo + '">삭제</button>'
      				}
    				} else {
    					str += '<span>삭제된 댓글입니다.</span>'
    				}
    				// 댓글 닫는 <div>
    				str += '</div>';
    				// 목록에 댓글 추가
    				commentList.append(str);
    			})
    			// 페이징 표시
    			paging.append(resData.paging);
    		},
    		error: (jqXHR) => {
    			alert(jqXHR.statusText + '(' + jqXHR.status + ')');
    		}
    	})
    }
    
    const fnBtnReply = () => {
    	$(document).on('click', '.btn-reply', (evt) => {
        // Sign In 체크
        fnCheckSignin();
        // 답글 작성 화면 조작하기
        let write = $(evt.target).next();
        if(write.hasClass('blind')) {
          $('.write').addClass('blind'); // 모든 답글 작성 화면 닫은 뒤
          write.removeClass('blind');    // 답글 작성 화면 열기
        } else {
          write.addClass('blind');
        }
      })
    }
    
    const fnBtnRemove = () => {
    	$(document).on('click', '.btn-remove', (evt) => {
  	     if(confirm('댓글을 삭제할까요?')) {
  	       location.href = '${contextPath}/blog/removeComment.do?commentNo=' + evt.target.dataset.commentNo + '&blogNo=${blog.blogNo}';
  	     }
  	   })
  	 }
    
    const fnPaging = (p) => {
    	page = p;
    	fnCommentList();
    }
    
    const fnRegisterReply = () => {
    	$(document).on('click', '.btn-register-reply', (evt) => {
        if('${sessionScope.user}' === ''){
          fnCheckSignin();
        }
        else{
          $.ajax({
            // 요청
            type: 'POST',
            url: '${contextPath}/blog/registerReply.do',
            data: $(evt.target).closest('.frm-reply').serialize(),
            // 응답
            dataType: 'json',
            success: (resData) => { // resData => {"insertCount": 1}
              if(resData.insertCount === 1){
                alert('댓글이 등록되었습니다.');
                $('#contents').val('');
                $(evt.target).prev().val('');
                // 댓글 목록 보여주는 함수
                fnCommentList();
              } else {
                alert('댓글 등록이 실패했습니다');
              }
            },
            error: (jqXHR) => {
              alert(jqXHR.statusText + '(' + jqXHR.status + ')');
            }
          })
        }
      })
    }

    fnBtnRemove();
    $('#contents').on('click', fnCheckSignin);
    fnRegisterComment();
    fnCommentList();
    fnBtnReply();
    fnRegisterReply();
  </script>
  
<%@ include file="../layout/footer.jsp" %>