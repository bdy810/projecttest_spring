package com.hk.board.dtos;

import java.util.Date;

public class addroomDto {
	private int seq;
	private String name;
	private String place;
	private String price;
	private String writer;
	private Date regdate;
	
	
	public addroomDto() {
		super();
	}


	public addroomDto(int seq, String name, String place, String price, String writer, Date regdate) {
		super();
		this.seq = seq;
		this.name = name;
		this.place = place;
		this.price = price;
		this.writer = writer;
		this.regdate = regdate;
	}


	public addroomDto(String name, String place, String price, String writer) {
		super();
		this.name = name;
		this.place = place;
		this.price = price;
		this.writer = writer;
	}
	

	public addroomDto(int seq, String name, String price) {
		super();
		this.seq = seq;
		this.name = name;
		this.price = price;
	}
	

	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public Date getRegdate() {
		return regdate;
	}


	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}


	@Override
	public String toString() {
		return "addroomDto [seq=" + seq + ", name=" + name + ", place=" + place + ", price=" + price + ", writer="
				+ writer + ", regdate=" + regdate + "]";
	}
	
	
}
