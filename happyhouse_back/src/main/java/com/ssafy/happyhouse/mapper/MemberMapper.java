package com.ssafy.happyhouse.mapper;

import java.sql.SQLException;

import com.ssafy.happyhouse.dto.MemberDto;


public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	
}