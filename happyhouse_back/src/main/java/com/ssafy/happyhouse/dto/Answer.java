package com.ssafy.happyhouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Answer (게시글정보)", description = "글번호, 제목, 내용, 작성자아이디, 작성일을 가진   Domain Class")
public class Answer {
	@ApiModelProperty(value = "글번호")
	private int questionno;
	@ApiModelProperty(value = "답변번호")
	private int answerno;
	@ApiModelProperty(value = "작성자아이디")
	private String userid;
	@ApiModelProperty(value = "내용")
	private String comment;
	@ApiModelProperty(value = "작성일")
	private String regtime;

	public int getQuestionno() {
		return questionno;
	}

	public void setQuestionno(int questionno) {
		this.questionno = questionno;
	}

	
	public int getAnswerno() {
		return answerno;
	}

	public void setAnswerno(int answerno) {
		this.answerno = answerno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	public Answer(int questionno, int answerno, String userid, String comment, String regtime) {
		super();
		this.questionno = questionno;
		this.answerno = answerno;
		this.userid = userid;
		this.comment = comment;
		this.regtime = regtime;
	}
}