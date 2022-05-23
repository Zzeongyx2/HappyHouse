package com.ssafy.happyhouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Reply (게시글정보)", description = "글번호, 제목, 내용, 작성자아이디, 작성일을 가진   Domain Class")
public class Reply {
	@ApiModelProperty(value = "글번호")
	private int communityno;
	@ApiModelProperty(value = "답변번호")
	private int replyno;
	@ApiModelProperty(value = "작성자아이디")
	private String userid;
	@ApiModelProperty(value = "내용")
	private String comment;
	@ApiModelProperty(value = "작성일")
	private String regtime;

	public int getQuestionno() {
		return communityno;
	}

	public void setQuestionno(int communityno) {
		this.communityno = communityno;
	}

	
	public int getReplyno() {
		return replyno;
	}

	public void setReplyno(int replyno) {
		this.replyno = replyno;
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

	public Reply(int communityno, int replyno, String userid, String comment, String regtime) {
		super();
		this.communityno = communityno;
		this.replyno = replyno;
		this.userid = userid;
		this.comment = comment;
		this.regtime = regtime;
	}
}