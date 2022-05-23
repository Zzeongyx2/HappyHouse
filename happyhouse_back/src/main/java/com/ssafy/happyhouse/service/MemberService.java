package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.MemberDto;
import com.ssafy.happyhouse.dto.Notice;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public boolean registerUser(MemberDto memberDto) throws Exception;
	public boolean modifyUser(MemberDto memberDto) throws Exception;
	public boolean deleteUser(String userid) throws Exception;
	public int idCheck(String userid) throws Exception;
}
