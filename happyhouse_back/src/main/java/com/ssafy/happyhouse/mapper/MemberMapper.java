package com.ssafy.happyhouse.mapper;

import java.sql.SQLException;

import com.ssafy.happyhouse.dto.MemberDto;


public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	public int registerUser(MemberDto memberDto) throws SQLException;
	public int updateUser(MemberDto memberDto) throws SQLException;
	public int updatePassword(MemberDto memberDto) throws SQLException;
	public int deleteUser(String userid) throws SQLException;
	public int idCheck(String userid) throws SQLException;
	public String findPWD(MemberDto memberDto) throws SQLException;
	public int calcUsers() throws SQLException;
	public int updateVisited(String userid) throws SQLException;
	public int getTotalVisited() throws SQLException;
}