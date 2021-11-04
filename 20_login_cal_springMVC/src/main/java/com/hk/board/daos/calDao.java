package com.hk.board.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hk.board.dtos.calDto;

@Repository
public class calDao implements Interface_calDao{
	
	private String namespace="com.hk.calboard.";
	
	//application-context.xml에 등록
	@Autowired   //mybatis와 연결. spring이 알아서 연결을 해준다. 제어의 역전
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public boolean calInsert(calDto dto) {
		int count = sqlSessionTemplate.insert(namespace+"calInsert", dto);
		return count>0?true:false;
	}

	@Override
	public List<calDto> getCalBoardList(String id, String ymd) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("ymd", ymd);
		return sqlSessionTemplate.selectList(namespace+"getCalboardList", map);
	}

	@Override
	public boolean mulDel(String[] cseqs) {
		Map<String, String[]>map = new HashMap<String, String[]>();
		map.put("cseqs", cseqs);
		int count = sqlSessionTemplate.delete(namespace+ "mulDel" , map);
		return count>0?true:false;
	}

	@Override
	public calDto calDetail(int cseq) {
		return sqlSessionTemplate.selectOne(namespace+"calDetail", cseq);
	}

	@Override
	public boolean calUpdate(calDto dto) {
		int count = sqlSessionTemplate.update(namespace+"calUpdate", dto);
		return count>0?true:false;
	}

	@Override
	public List<calDto> calBoardListView(String id, String yyyyMM) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("yyyyMM", yyyyMM);
		return sqlSessionTemplate.selectList(namespace+"calBoardListView", map);
	}

	@Override
	public int calListCount(String id, String yyyyMMdd) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("yyyyMMdd", yyyyMMdd);
		return sqlSessionTemplate.selectOne(namespace+"calListCount", map);
	}

}
