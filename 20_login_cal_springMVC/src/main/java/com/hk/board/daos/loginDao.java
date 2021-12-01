package com.hk.board.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hk.board.dtos.loginDto;

@Repository
public class loginDao implements Interface_loginDao{
	
	private String namespace="com.hk.board.";
	
	//application-context.xml�� ���
	@Autowired   //mybatis�� ����. spring�� �˾Ƽ� ������ ���ش�. ������ ����
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public loginDto getLogin(String id, String password) {
		loginDto dto = null;
		Map<String, String>map = new HashMap<String, String>();
		map.put("id", id);
		map.put("password", password);
		dto=sqlSessionTemplate.selectOne(namespace+"getLogin", map);
		return dto;
	}

	@Override
	public boolean insertUser(loginDto dto) {
		int count = sqlSessionTemplate.insert(namespace+"insertUser", dto);
		return count>0?true:false;
	}

	@Override
	public String idChk(String id) {
		String resultId=null;
		Map<String, String>map = new HashMap<String, String>();
		map.put("id", id);
		resultId=sqlSessionTemplate.selectOne(namespace+"idChk", map);
		return resultId;
	}
// ���� ����
	@Override
	public loginDto getUser(String id) {
		loginDto dto = null;
		dto = sqlSessionTemplate.selectOne(namespace+"getUser", id);
		return dto;
	}
//��������Ʈ
	@Override
	public List<loginDto> getUserList() {
		List<loginDto> list = null;
		list = sqlSessionTemplate.selectList(namespace+"getUserList");
		return list;
	}

	@Override
	public String getRole(String id) {
		String role = null;
		role = sqlSessionTemplate.selectOne(namespace+"getRole",id);
		return role;
	}
	
}
