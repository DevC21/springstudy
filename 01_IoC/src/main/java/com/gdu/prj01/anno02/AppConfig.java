package com.gdu.prj01.anno02;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // IoC Container 에 bean 을 등록하는 클래스
public class AppConfig {

	/*
	 * 반환타입 : bean 의 타입, <bean class="">
	 * 메소드명 : bean 의 이름, <bean id="">
	 */
	@Bean
	public Student student() {
		Student student = new Student();
		student.setScores(Arrays.asList(90, 91, 92));
		student.setContacts(new HashSet<String>(Arrays.asList("02-1111-1111", "010-2222-2222")));
		student.setFriends(Map.of("동네친구", "길동", "운동친구", "또치"));
		return student;
	}
}
