package com.gdu.myapp.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UserMapper;
import com.gdu.myapp.utils.MyJavaMailUtils;
import com.gdu.myapp.utils.MySecurityUtils;

@PropertySource(value = "classpath:naver.properties")
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private Environment env;
  private final UserMapper userMapper;
  private final MyJavaMailUtils myJavaMailUtils;
  
  public UserServiceImpl(UserMapper userMapper, MyJavaMailUtils myJavaMailUtils) {
		super();
		this.userMapper = userMapper;
		this.myJavaMailUtils = myJavaMailUtils;
	}
  
  public ResponseEntity<Map<String, Object>> checkEmail(Map<String, Object> params) {
  	boolean enableEmail = userMapper.getUserByMap(params) == null 
  										 && userMapper.getLeaveUserBymap(params) == null;
  	
  	return new ResponseEntity<>(Map.of("enableEmail", enableEmail)
  														, HttpStatus.OK);
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> sendCode(Map<String, Object> params) {
  	
    /*
     * 구글 앱 비밀번호 설정 방법
     * 1. 구글에 로그인한다.
     * 2. [계정] - [보안]
     * 3. [Google에 로그인하는 방법] - [2단계 인증]을 사용 설정한다.
     * 4. 검색란에 "앱 비밀번호"를 검색한다.
     * 5. 앱 이름을 "myapp"으로 작성하고 [만들기] 버튼을 클릭한다.
     * 6. 16자리 비밀번호가 나타나면 복사해서 사용한다. (비밀번호 사이 공백은 모두 제거한다.)
     */
  	
  	// 인증코드 생성
  	String code = MySecurityUtils.getRandomString(6, true, true);
  	
  	// 개발 할 때 인증코드 찍어보기
  	System.out.println("인증코드 :" + code);
  	
  	
  	// 메일 보내기
  	myJavaMailUtils.sendMail((String)params.get("email")
  												 , "myapp 인증요청" 
  												 , "<div>인증코드는 <strong>" + code + "</strong>입니다.");
  	
  	// 인증코드 입력화면으로 보내줄 값
  	return new ResponseEntity<>(Map.of("code", code)
  													  , HttpStatus.OK);
  }

  @Override
  public void signup(HttpServletRequest request, HttpServletResponse response) {
  	
  	// 전달된 파라미터
  	String email = request.getParameter("email");
  	String pw = MySecurityUtils.getSha256(request.getParameter("pw"));
  	String name = MySecurityUtils.getPreventXss(request.getParameter("name")) ;
  	String mobile = request.getParameter("mobile");
  	String gender = request.getParameter("gender");
  	String event = request.getParameter("event");
  	
  	UserDto user = UserDto.builder()
  										 .email(email)
  										 .pw(pw)
  										 .name(name)
  										 .mobile(mobile)
  										 .gender(gender)
                       .eventAgree(event == null ? 0 : 1)
  										.build();
  	
  	// 회원 가입
  	int insertCount = userMapper.insertUser(user);
  	

  	// 응답 만들기 (성공하면 sign in 처리하고 /main.do 이동, 실패하면 뒤로 가기)
    
    try {
  		response.setContentType("text/html; charset=UTF-8");
  		PrintWriter out = response.getWriter();
  		out.println("<script>");
  		
    	// 일치하는 회원이 있음 (Sign In 성공)
    	if(insertCount == 1) {
      	Map<String, Object> params = Map.of("email", email,"pw", pw , "ip", request.getRemoteAddr());

      	// 접속 기록 ACCESS_HISTORY_T 에 남기기
    		userMapper.insertAccessHistory(params);

    		// 회원 정보를 세션에 보관하기
    		request.getSession().setAttribute("user", userMapper.getUserByMap(params));
    		
    		// Sign In 후 페이지 이동
        out.println("location.href='" + request.getContextPath() + "/main.page';");
    		
        // 일치하는 회원이 없음 (Sign In 실패)
    	} else {
    		out.println("alert('일치하는 회원 정보가 없습니다.')");
        out.println("history.back();");
    	}
    	
    	out.println("</script>");
    	out.flush();
    	out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
  	
  }
  
  @Override
  public void leave(HttpServletRequest request, HttpServletResponse response) {
  	
    try {
    	// 세션에 저장된 user 값 확인
    	UserDto user = (UserDto) request.getSession().getAttribute("user");
    	
    	// 세션 만료로 user 정보가 세션에 없을 수 있음
    	if(user == null) {
    		response.sendRedirect(request.getContextPath() + "/main.page");
    	}
    	
    	int deleteCount = userMapper.deleteUser(user.getUserNo());
    	
  		response.setContentType("text/html; charset=UTF-8");
  		PrintWriter out = response.getWriter();
  		out.println("<script>");
    	// 일치하는 회원이 있음 (leave 성공)
    	if(deleteCount == 1) {

    		// 세션에 저장된 모든 정보 초기화
    		request.getSession().invalidate(); // SessionStatus 객체의 setComplete() 메소드 호출
    		
    		// leave 후 페이지 이동
    		out.println("alert('회원 탈퇴 되었습니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.page';");
    		
        // 일치하는 회원이 없음 (leave 실패)
    	} else {
    		out.println("alert('회원 탈퇴를 실패하였습니다.')");
        out.println("history.back();");
    	}
    	out.println("</script>");
    	out.flush();
    	out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
  	
  }
  
  @Override
  public String getRedirectURLAfterSignin(HttpServletRequest request) {
    // Sign In 페이지 이전의 주소가 저장되어 있는 Request Header 의 referer
    String referer = request.getHeader("referer");
    
    // referer 로 돌아가면 안 되는 예외 상황 (아이디/비밀번호 찾기 화면, 가입 화면 등)
    String[] excludeUrls = {"/findId.page", "/findPw.page", "/signup.page"};
    
    // Sign In 이후 이동할 url
    String url = referer;
    if(referer != null) {
      for(String excludeUrl : excludeUrls) {
        if(referer.contains(excludeUrl)) {
          url = request.getContextPath() + "/main.page";
          break;
        }
      }
    } else {
      url = request.getContextPath() + "/main.page";
    }
    
  	return url;
  }
  
	@Override
  public void signin(HttpServletRequest request, HttpServletResponse response) {
    
    try {
      
    	// 입력한 아이디
      String email = request.getParameter("email");
      // 입력한 비밀번호 + SHA-256 방식의 암호화
      String pw = MySecurityUtils.getSha256(request.getParameter("pw"));
      // 접속 IP (접속 기록을 남길 때 필요한 정보)
      String ip = request.getRemoteAddr();
      
       // DB로 보낼 정보 (email/pw : USER_T , email/ip : ACCESS_HISTORY_T)
      Map<String, Object> params = Map.of("email", email
                                        , "pw", pw
                                        , "ip", ip);
      
      // email/pw 가 일치하는 회원 정보 가져오기
      UserDto user = userMapper.getUserByMap(params);
      
      // 일치하는 회원이 있음 (Sign In 성공)
      if(user != null) {
      	// 접속 기록 ACCESS_HISTORY_T 에 남기기
      	userMapper.insertAccessHistory(params);
      	// 회원 정보를 세션에 보관하기
      	// 세션 : 브라우저 닫기 전까지 정보가 유지되는 공간, 기본 30분 정보 유지
        request.getSession().setAttribute("user", user);
        // Sign In 후 페이지 이동
        response.sendRedirect(request.getParameter("url"));
      // 일치하는 회원이 없음 (Sign In 실패)
      } else {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('일치하는 회원 정보가 없습니다.')");
        out.println("location.href='" + request.getHeader("referer") + "'");
//        out.println("location.href='" + request.getContextPath() + "/main.page");
        out.println("</script>");
        out.flush();
        out.close();
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Override
  public void signout(HttpServletRequest request, HttpServletResponse response) {

    try {
    	
  		response.setContentType("text/html; charset=UTF-8");
  		PrintWriter out = response.getWriter();
  		out.println("<script>");

  		// 세션에 저장된 모든 정보 초기화
  		request.getSession().invalidate(); // or SessionStatus 객체의 setComplete() 메소드 호출
  		
  		// signout 후 페이지 이동
  		out.println("alert('로그아웃 되었습니다.')");
      out.println("location.href='" + request.getContextPath() + "/main.page';");
    	out.println("</script>");
    	out.flush();
    	out.close();
    	
		} catch (Exception e) {
			e.printStackTrace();
		}

  }
  
  @Override
  public String getNaverLoginURL(HttpServletRequest request) {
  	// 네이버 로그인 1
    // 네이버 로그인 요청 주소를 만들어서 반환하는 메소드
    String redirectUri = "http://localhost:8080" + request.getContextPath() + "/user/naver/getAccessToken.do";
    String state = new BigInteger(130, new SecureRandom()).toString();
    StringBuilder builder = new StringBuilder();
    builder.append("https://nid.naver.com/oauth2.0/authorize");
    builder.append("?response_type=code");
    builder.append("&client_id=" + env.getProperty("spring.naver.clientid"));	
    builder.append("&redirect_uri=" + redirectUri);
    builder.append("&state=" + state);
    
    
  	return builder.toString();
  }

  @Override
  public String getNaverLoginAccessToken(HttpServletRequest request) {
  	
    /************* 네이버 로그인 2 *************/
    // 네이버로부터 Access Token 을 발급 받아 반환하는 메소드
    // 네이버 로그인 1단계에서 전달한 redirect_uri 에서 동작하는 서비스
    // code 와 state 파라미터를 받아서 Access Token 을 발급 받을 때 사용
    
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    
    String spec = "https://nid.naver.com/oauth2.0/token";
    String grantType = "authorization_code";
    String clientId = env.getProperty("spring.naver.clientid");
    String clientSecret = env.getProperty("spring.naver.clientsecret");
    
    StringBuilder builder = new StringBuilder();
    builder.append(spec);
    builder.append("?grant_type=" + grantType);
    builder.append("&client_id=" + clientId);
    builder.append("&client_secret=" + clientSecret);
    builder.append("&code=" + code);
    builder.append("&state=" + state);
    
    HttpURLConnection con = null;
    JSONObject obj = null;
    try {
    	
    	// 요청
    	URL url = new URL(builder.toString());
    	con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET"); // 반드시 대문자로 작성해야 한다.
    	
    	// 응답 스트림 생성
      BufferedReader reader = null;
      int responseCode = con.getResponseCode();
      if(responseCode == HttpURLConnection.HTTP_OK) {
      	reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {
      	reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      
      // 응답 데이터 받기
      String line = null;
      StringBuilder responseBody = new StringBuilder();
      while((line = reader.readLine()) != null) {
      	responseBody.append(line);
      }
      
      // 응답 데이터를 JSON 객체로 변환하기
      obj = new JSONObject(responseBody.toString());
      
      // 응답 스트림 닫기
      reader.close();
      
		} catch (Exception e) {
			e.printStackTrace();
		}
    
    con.disconnect();
    
    return obj.getString("access_token");
  }
  
  @Override
  public UserDto getNaverLoginProfile(String accessToken) {
    /************* 네이버 로그인 3 *************/
    // 네이버로부터 프로필 정보(이메일, [이름, 성별, 휴대전화번호]) 을 발급 받아 반환하는 메소드
  	
  	String spec = "https://openapi.naver.com/v1/nid/me";
  	
  	HttpURLConnection con = null;
  	UserDto user = null;
    
    try {
		
    	// 요청
    	URL url = new URL(spec);
    	con = (HttpURLConnection) url.openConnection();
    	
    	// 요청 헤더
    	con.setRequestProperty("Authorization", "Bearer " + accessToken);
    	
    	// 응답 스트림 생성
      BufferedReader reader = null;
      int responseCode = con.getResponseCode();
      if(responseCode == HttpURLConnection.HTTP_OK) {
      	reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {
      	reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      
      // 응답 데이터 받기
      String line = null;
      StringBuilder responseBody = new StringBuilder();
      while((line = reader.readLine()) != null) {
      	responseBody.append(line);
      }
      
      // 응답 데이터를 JSON 객체로 변환하기
      JSONObject obj = new JSONObject(responseBody.toString());
      JSONObject response = obj.getJSONObject("response");
      user = UserDto.builder()
              		.email(response.getString("email"))
      						.name(response.has("name") ? response.getString("name") : null)
      						.gender(response.has("gender") ? response.getString("gender") : null)
      						.mobile(response.has("mobile") ? response.getString("mobile") : null)
      					.build();
      
      // 응답 스트림 닫기
      reader.close();
    	
		} catch (Exception e) {
			e.printStackTrace();
		}
  	
    con.disconnect();
  	
  	return user;
  }
  
  @Override
  public boolean hasUser(UserDto user) {
  	return userMapper.getUserByMap(Map.of("email", user.getEmail())) != null;
  }
  
  @Override
  public void naverSignin(HttpServletRequest request, UserDto naverUser) {
  	
  	Map<String, Object> map = Map.of("email", naverUser.getEmail()
  																	,"ip", request.getRemoteAddr());
  	
  	UserDto user = userMapper.getUserByMap(map);
  	request.getSession().setAttribute("user", user);
  	userMapper.insertAccessHistory(map);
  	
  }
}