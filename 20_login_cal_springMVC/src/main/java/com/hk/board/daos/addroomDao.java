package com.hk.board.daos;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hk.board.dtos.addroomDto;
@Repository
public class addroomDao implements Interface_addroomDao{
	private String namespace="com.hk.addroom.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<addroomDto> getRoomList() {
		return sqlSession.selectList(namespace+"getRoomList");
	}

	@Override
	public boolean insertRoom(addroomDto dto) {
		int count = sqlSession.insert(namespace+"insertRoom", dto);
		return count>0?true:false;
	}

	@Override
	public addroomDto detailRoom(int seq) {
		
		return null;
	}

	@Override
	public boolean updateRoom(addroomDto dto) {
		
		return false;
	}

	@Override
	public boolean delRoom(int seq) {
		
		return false;
	}

	@Override
	public boolean mulDel(String[] seqs) {
		
		return false;
	}
}
