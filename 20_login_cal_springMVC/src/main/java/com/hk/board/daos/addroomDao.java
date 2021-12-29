package com.hk.board.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		return sqlSession.selectOne(namespace+"detailroom",seq);
	}

	@Override
	public boolean updateRoom(addroomDto dto) {
		int count=sqlSession.update(namespace+"updateroom", dto);
		return count>0?true:false;
	}

	@Override
	public boolean delRoom(int seq) {
		int count = sqlSession.delete(namespace+"delRoom", seq);
		return count>0?true:false;
	}

	@Override
	public boolean mulDel(String[] seqs) {
		Map<String, String[]>map = new HashMap<>();
		map.put("seqs", seqs);
		int count = sqlSession.delete(namespace+"muldel", map);
		return count>0?true:false;
	}
}
