package com.gdu.prj01.anno03;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class AppConfig {

	@Bean
	public MyConnection myConnection() {
		MyConnection myConnection = new MyConnection();
		myConnection.setDriver("oracle.jdbc.driver.OracleDriver");
		myConnection.setUrl("jdbc:oracle:thin:@192.168.8.59:1521:xe");
		myConnection.setUser("GD");
		myConnection.setPassword("1111");
		return myConnection;
	}
	
	@Bean
	public MyDao myDao() {
		MyDao myDao = new MyDao();
		myDao.setMyConnection(myConnection());
		return myDao;
	}
	
	@Bean
	public MyService mySerivce() {
		MyService mySerivce = new MyService();
		mySerivce.setMyDao(myDao());
		return mySerivce;
	}
	
	@Bean
	public MyController myController() {
		MyController myController = new MyController();
		myController.setMyService(mySerivce());
		return myController;
	}
}
