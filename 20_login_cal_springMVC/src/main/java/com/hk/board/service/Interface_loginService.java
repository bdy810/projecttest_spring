package com.hk.board.service;


import com.hk.board.dtos.loginDto;



public interface Interface_loginService {
	//�α���
	public loginDto getLogin(String id, String password);
	// ȸ������ 
	public boolean insertUser(loginDto dto); 
	
	//���̵� �ߺ�üũ: ������ ���̵� ���� DB�� �����ϴ� ���� üũ-select������, �ĸ����� : ������ ID
	public String idChk(String id);
}
