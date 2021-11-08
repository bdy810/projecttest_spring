package com.hk.board.daos;

import java.util.List;

import com.hk.board.dtos.loginDto;


public interface Interface_loginDao {
	//�α���
	public loginDto getLogin(String id, String password);
	// ȸ������ 
	public boolean insertUser(loginDto dto); 
	
	//���̵� �ߺ�üũ: ������ ���̵� ���� DB�� �����ϴ� ���� üũ-select������, �ĸ����� : ������ ID
	public String idChk(String id);
	
	public loginDto getUser(String id);
	
	public List<loginDto> getUserList();
}
