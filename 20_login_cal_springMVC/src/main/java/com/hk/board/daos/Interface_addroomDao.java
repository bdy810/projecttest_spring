package com.hk.board.daos;

import java.util.List;
import com.hk.board.dtos.addroomDto;

public interface Interface_addroomDao {
	public List<addroomDto> getRoomList();
	public boolean insertRoom(addroomDto dto);
	public addroomDto detailRoom(int seq);
	public boolean updateRoom(addroomDto dto);
	public boolean delRoom(int seq);
	public boolean mulDel(String[] seqs);	
}
