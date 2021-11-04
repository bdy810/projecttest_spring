package com.hk.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hk.board.daos.Interface_calDao;
import com.hk.board.dtos.calDto;


@Service
public class calService implements Interface_calService{
	
	@Autowired
	private Interface_calDao caldao;

	@Override
	public boolean calInsert(calDto dto) {
		return caldao.calInsert(dto);
	}

	@Override
	public List<calDto> getCalBoardList(String id, String ymd) {
		return caldao.getCalBoardList(id, ymd);
	}

	@Override
	public boolean mulDel(String[] cseqs) {
		return caldao.mulDel(cseqs);
	}

	@Override
	public calDto calDetail(int cseq) {
		return caldao.calDetail(cseq);
	}

	@Override
	public boolean calUpdate(calDto dto) {
		return caldao.calUpdate(dto);
	}

	@Override
	public List<calDto> calBoardListView(String id, String yyyyMM) {
		return caldao.calBoardListView(id, yyyyMM);
	}

	@Override
	public int calListCount(String id, String yyyyMMdd) {
		return caldao.calListCount(id, yyyyMMdd);
	}
	
	
	
}
