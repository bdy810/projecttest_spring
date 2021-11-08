package com.hk.board.dtos;

import java.util.Date;

public class calDto {
	private int cseq;
	private String id;
	private String caltitle;
	private String calcontent;
	private String mdate;
	private Date calregdate;
	
	//join할 경우 사용할 객체 선언: calboard와 userinfo
	private loginDto Logindto;
	
	public calDto() {
		super();
	}
	
	
	
	public calDto(String id, String caltitle, String calcontent, String mdate) {
		super();
		this.id = id;
		this.caltitle = caltitle;
		this.calcontent = calcontent;
		this.mdate = mdate;
	}



	public calDto(int cseq, String id, String caltitle, String calcontent, String mdate, Date calregdate) {
		super();
		this.cseq = cseq;
		this.id = id;
		this.caltitle = caltitle;
		this.calcontent = calcontent;
		this.mdate = mdate;
		this.calregdate = calregdate;
	}

	public loginDto getLogindto() {
		return Logindto;
	}

	public void setLogindto(loginDto logindto) {
		Logindto = logindto;
	}

	public int getCseq() {
		return cseq;
	}

	public void setCseq(int cseq) {
		this.cseq = cseq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaltitle() {
		return caltitle;
	}

	public void setCaltitle(String caltitle) {
		this.caltitle = caltitle;
	}

	public String getCalcontent() {
		return calcontent;
	}

	public void setCalcontent(String calcontent) {
		this.calcontent = calcontent;
	}

	public String getMdate() {
		return mdate;
	}

	public void setMdate(String mdate) {
		this.mdate = mdate;
	}

	public Date getCalregdate() {
		return calregdate;
	}

	public void setCalregdate(Date calregdate) {
		this.calregdate = calregdate;
	}

	@Override
	public String toString() {
		return "calDto [cseq=" + cseq + ", id=" + id + ", caltitle=" + caltitle + ", calcontent=" + calcontent
				+ ", mdate=" + mdate + ", calregdate=" + calregdate + "]";
	}
	
	
	
	
}
