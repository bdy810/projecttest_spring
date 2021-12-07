package com.hk.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hk.board.daos.Interface_addroomDao;
import com.hk.board.dtos.addroomDto;

@Service
public class addroomService implements Interface_addroomService{
	@Autowired
	private Interface_addroomDao addroomDao;
	
	@Override
	public List<addroomDto> getRoomList() {
		return addroomDao.getRoomList();
	}

	@Override
	public boolean insertRoom(addroomDto dto) {
		return addroomDao.insertRoom(dto);
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
