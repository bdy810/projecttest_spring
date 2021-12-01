package com.hk.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hk.board.daos.Interface_noticeDao;
import com.hk.board.dtos.noticeDto;
@Service
public class noticeService implements Interface_noticeService{
	
	@Autowired
	private Interface_noticeDao noticeDao;

	@Override
	public List<noticeDto> getBoardList() {
		return noticeDao.getBoardList();
	}

	@Override
	public boolean insertBoard(noticeDto dto) {
		return noticeDao.insertBoard(dto);
	}

	@Override
	public noticeDto getBoard(int seq) {
		return noticeDao.getBoard(seq);
	}

	@Override
	public boolean updateBoard(noticeDto dto) {
		return noticeDao.updateBoard(dto);
	}

	@Override
	public boolean delBoard(int seq) {
		return false;
	}

	@Override
	public boolean mulDel(String[] seqs) {
		return noticeDao.mulDel(seqs);
	}

}
