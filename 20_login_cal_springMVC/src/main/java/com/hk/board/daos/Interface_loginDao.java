package com.hk.board.daos;

import java.util.List;

import com.hk.board.dtos.loginDto;


public interface Interface_loginDao {
	//로그인
	public loginDto getLogin(String id, String password);
	// 회원가입 
	public boolean insertUser(loginDto dto); 
	
	//아이디 중복체크: 가입할 아이디가 기존 DB에 존재하는 여부 체크-select문실행, 파리미터 : 가입할 ID
	public String idChk(String id);
	
	public loginDto getUser(String id);
	
	public List<loginDto> getUserList();
}
