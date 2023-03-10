package com.ssafy.happyhouse.service;

import java.sql.SQLException;

import com.ssafy.happyhouse.dto.MemberDto;
import com.ssafy.happyhouse.dto.Notice;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public boolean registerUser(MemberDto memberDto) throws Exception;
	public boolean updateUser(MemberDto memberDto) throws Exception;
	public boolean updatePassword(MemberDto memberDto) throws Exception; 
	public boolean deleteUser(String userid) throws Exception;
	public int idCheck(String userid) throws Exception;
	public String findPWD(MemberDto memberDto) throws Exception;
	public int calcUsers() throws Exception;
	public int updateVisited(String userid) throws Exception;
	public int getTotalVisited() throws Exception;
}
