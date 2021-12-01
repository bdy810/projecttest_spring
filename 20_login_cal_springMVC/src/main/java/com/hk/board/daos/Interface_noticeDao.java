package com.hk.board.daos;

import java.util.List;

import com.hk.board.dtos.noticeDto;

public interface Interface_noticeDao {
	public List<noticeDto> getBoardList();
	public boolean insertBoard(noticeDto dto);
	public noticeDto getBoard(int seq);
	public boolean updateBoard(noticeDto dto);
	public boolean delBoard(int seq);
	public boolean mulDel(String[] seqs);	
}
