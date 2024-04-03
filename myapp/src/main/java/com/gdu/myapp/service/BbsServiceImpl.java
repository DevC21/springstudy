package com.gdu.myapp.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.myapp.mapper.BbsMapper;
import com.gdu.myapp.utils.MyPageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BbsServiceImpl implements BbsService {

	private final BbsMapper bbsMapper;
	private final MyPageUtils myPageUtils;
	
	@Override
	public int registerBbs(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void loadBbsList(HttpServletRequest request, Model model) {
		// TODO Auto-generated method stub

	}

	@Override
	public int registerReply(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int removeBbs(int bbsNo) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void loadBbsSearchList(HttpServletRequest request, Model model) {
		// TODO Auto-generated method stub
		
	}

}
