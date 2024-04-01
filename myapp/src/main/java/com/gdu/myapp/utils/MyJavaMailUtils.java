package com.gdu.myapp.utils;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MyJavaMailUtils {
	
	public void sendMail(String to, String subject, String contents) {
		
		// 이메일을 보내는 호스트의 정보 : 구글
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		
		// javax.mail.Session 객체 생성 : 이메일을 보내는 사용자의 정보 (개인 정보)
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("gmail", "password");
			}
		});
		
		try {
			// 메일 만들기 (보내는 사람 + 받는 사람 + 제목 + 내용)
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress("gmail", "myapp"));
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setSubject(subject);
			mimeMessage.setContent(contents, "text/html; charset=UTF-8");
			// 메일 보내기
			Transport.send(mimeMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
