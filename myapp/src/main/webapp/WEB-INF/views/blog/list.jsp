<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>

 <jsp:include page="../layout/header.jsp">
   <jsp:param value="블로그 리스트" name="title"/>
 </jsp:include>
 
 <style>
 
  .blog{
    width: 640px;
    height: 180px;
    margin: 10px auto;
    border: 1px solid gray;
    cursor: pointer;
  }
  
  .blog > span {
    color: tomato;
    display: inline-block;
    box-sizing: border-box;
  }
  
  .blog > span:nth-of-type(1){
    width: 150px;

  }
  
  .blog > span:nth-of-type(2){
    width: 250px;
  }
  
  .blog > span:nth-of-type(3){
    width: 50px;
  }
  
  .blog > span:nth-of-type(4){
    width: 150px;
  }
 
 </style>
 
  <h1 class="title">블로그 목록</h1>
  
  <a href="${contextPath}/blog/write.page">블로그작성</a>
  
  <div id="blog-list">
  
  </div>
  
  
 
  
  <script>
  
    // 전역 변수
    var page = 1;
    var totalPage = 0;
    
    const fnGetBlogList = () => {
    	
    	// page에 해당하는 목록 요청	
    	  $.ajax({
  		    // 요청
  		    type: 'GET',
  		    url: '${contextPath}/blog/getBlogList.do',
  		    data: 'page=' + page,
  		    // 응답
  		    dataType: 'json',
  		    success: (resData) => {  // resData = {"blogList": [], "totalPage": 10}
  		      totalPage = resData.totalPage;
  		      $.each(resData.blogList, (i, blog) => {
  		        let str = '<div class="blog" data-blog-no="' + blog.blogNo + '">';
  		        str += '<span>' + blog.title + '</span>';
  		        str += '<span>' + blog.user.email + '</span>';
  		        str += '<span>' + blog.hit + '</span>';
  		        str += '<span>' + moment(blog.createDt).format('YYYY.MM.DD.') + '</span>';
  		        str += '</div>';
  		        $('#blog-list').append(str);
  		      })
  		    },
  		    error: (jqXHR) => {
  		      alert(jqXHR.statusText + '(' + jqXHR.status + ')');
  		    }
  		  });
      
    }
    
    const fnScrollHander = () => {
    	// 스크롤이 바닥에 닿으면 page 증가(최대 totalPage 까지) 후 새로운 목록 요청

    	  // 타이머 id (동작한 타이머의 동작 취소를 위한 변수)
    	  var timerId;  // undefined, boolean 의 의미로는 false
    	  $(window).on('scroll', (evt) => {
    	    if(timerId) {  // timerId 가 undefined 이면 false, 아니면 true 
    	                   // timerId 가 undefined 이면 setTimeout() 함수가 동작한 적 없음
    	      clearTimeout(timerId);  // setTimeout() 함수 동작을 취소함 -> 목록을 가져오지 않는다.
    	    }
    	    // 500밀리초(0.5초) 후에 () => {}가 동작하는 setTimeout 함수
    	    timerId = setTimeout(() => {
    	      let scrollTop = $(window).scrollTop();
    	      let windowHeight = $(window).height();
    	      let documentHeight = $(document).height();
    	      if( (scrollTop + windowHeight + 50) >= documentHeight ) {  // 스크롤과 바닥 사이 길이가 50px 이하인 경우 
    	        if(page > totalPage) {
    	          return;
    	        }
    	        page++;
    	        fnGetBlogList();
    	      }
    	    }, 500);
    	})
    }
  
    const fnBlogDetail = () => {
    	
    	$(document).on('click', '.blog', (evt) => {
    		
    		// <div class="blog"> 중 클릭 이벤트가 발생한 <div> : 이벤트 대상 evt.target
    		// evt.target.dataset.blogNo === $(evt.target).data('blogNo')
    		
    		location.href = '${contextPath}/blog/detail.do?blogNo=' + evt.target.dataset.blogNo;
    		
    	})
    	
    }
    
  
    const fnInsertCount = () => {
  	  let insertCount = '${insertCount}';
  	  if(insertCount !== ''){
  		  if(insertCount === '1') {
  			  alert('블로그가 등록되었습니다.');
  		  } else {
  			  alert('블로그 등록이 실패했습니다.');;
  		  }
  	  }
    }
    
    fnGetBlogList();
    fnScrollHander();
    fnBlogDetail();
    fnInsertCount();
    
  </script>
  
  
<%@ include file="../layout/footer.jsp" %>