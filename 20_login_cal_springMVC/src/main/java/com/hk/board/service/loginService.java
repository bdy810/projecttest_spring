package com.hk.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hk.board.daos.Interface_loginDao;
import com.hk.board.daos.loginDao;
import com.hk.board.dtos.loginDto;


@Service
public class loginService implements Interface_loginService{
	
	@Autowired //autowired는 선언 하나당 한번씩 써줘야한다.
	private Interface_loginDao logindao;

	@Override
	public loginDto getLogin(String id, String password) {
		return logindao.getLogin(id, password);
	}

	@Override
	public boolean insertUser(loginDto dto) {
		return logindao.insertUser(dto);
	}

	@Override
	public String idChk(String id) {
		return logindao.idChk(id);
	}

	@Override
	public loginDto getUser(String id) {
		return logindao.getUser(id);
	}

	@Override
	public List<loginDto> getUserList() {
		return logindao.getUserList();
	}

}
