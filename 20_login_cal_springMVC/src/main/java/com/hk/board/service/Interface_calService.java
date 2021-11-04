package com.hk.board.service;

import java.util.List;

import com.hk.board.dtos.calDto;


public interface Interface_calService {
	
	   public boolean calInsert(calDto dto);
	   public List<calDto> getCalBoardList(String id, String ymd);
	   public boolean mulDel(String[] cseqs);
	   public calDto calDetail(int cseq);
	   public boolean calUpdate(calDto dto);
	   public List<calDto> calBoardListView(String id, String yyyyMM);
	   public int calListCount(String id, String yyyyMMdd);
}
