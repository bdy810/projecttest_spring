package com.hk.board.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hk.board.dtos.noticeDto;

@Repository
public class noticeDao implements Interface_noticeDao{
	
	private String namespace="com.hk.notice.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<noticeDto> getBoardList() {
		return sqlSession.selectList(namespace+"getBoardList");
	}

	@Override
	public boolean insertBoard(noticeDto dto) {
		int count=sqlSession.insert(namespace+"insertBoard", dto);
	    return count>0?true:false;
	}

	@Override
	public noticeDto getBoard(int seq) {
		return sqlSession.selectOne(namespace+"getBoard", seq);
	}

	@Override
	public boolean updateBoard(noticeDto dto) {
		int count = sqlSession.update(namespace+"updateBoard", dto);
		return count>0?true:false;
	}

	@Override
	public boolean delBoard(int seq) {
		int count = sqlSession.delete(namespace+"delBoard", seq);
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
