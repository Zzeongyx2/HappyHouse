package com.ssafy.happyhouse.dto;

public class Region {
	String userid;
	String sido;
	String gugun;
	String dong;
	String dongcode;
	String regtime;
	
	public Region() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Region(String userid, String sido, String gugun, String dong) {
		this.userid = userid;
		this.sido = sido;
		this.gugun = gugun;
		this.dong = dong;
	}
	
	public Region(String userid, String sido, String gugun, String dong, String dongcode, String regtime) {
		this.userid = userid;
		this.sido = sido;
		this.gugun = gugun;
		this.dong = dong;
		this.dongcode = dongcode;
		this.regtime = regtime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getGugun() {
		return gugun;
	}

	public void setGugun(String gugun) {
		this.gugun = gugun;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getDongcode() {
		return dongcode;
	}

	public void setDongcode(String dongcode) {
		this.dongcode = dongcode;
	}
	
	
	

}
