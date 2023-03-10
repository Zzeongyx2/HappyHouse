package com.ssafy.happyhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.MemberDto;
import com.ssafy.happyhouse.mapper.MemberMapper;
import com.ssafy.happyhouse.util.Hash;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if(memberDto.getUserid() == null || memberDto.getUserpwd() == null)
			return null;
		
		// 비밀번호 암호화
		String pwd = memberDto.getUserpwd();

		Hash hash = new Hash(pwd);
		hash.setEncryptData(hash.getPw());
		String enc = hash.getEncryptData();

		memberDto.setUserpwd(enc);

		
		return memberMapper.login(memberDto);
	}

	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return memberMapper.userInfo(userid);
	}

	@Override
	public boolean registerUser(MemberDto memberDto) throws Exception {
		// 비밀번호 암호화
		Hash hash = new Hash(memberDto.getUserpwd());
		// encryption
		hash.setEncryptData(hash.getPw());
		String enc = hash.getEncryptData();
		memberDto.setUserpwd(enc);
		
		return memberMapper.registerUser(memberDto) == 1;
		
	}

	@Override
	public boolean deleteUser(String userid) throws Exception {
		return memberMapper.deleteUser(userid) == 1;
	}

	@Override
	public boolean updateUser(MemberDto memberDto) throws Exception {
		return memberMapper.updateUser(memberDto) == 1;
	}

	@Override
	public boolean updatePassword(MemberDto memberDto) throws Exception {
		// 비밀번호 암호화
		Hash hash = new Hash(memberDto.getUserpwd());
		// encryption
		hash.setEncryptData(hash.getPw());
		String enc = hash.getEncryptData();
		memberDto.setUserpwd(enc);
		return memberMapper.updatePassword(memberDto) == 1;
	}
	
	@Override
	public int idCheck(String userid) throws Exception {
		return memberMapper.idCheck(userid);
	}

	@Override
	public String findPWD(MemberDto memberDto) throws Exception {
		return memberMapper.findPWD(memberDto);
	}

	@Override
	public int calcUsers() throws Exception {
		return memberMapper.calcUsers();
	}
	
	@Override
	public int getTotalVisited() throws Exception {
		return memberMapper.getTotalVisited();
	}

	@Override
	public int updateVisited(String userid) throws Exception {
		return memberMapper.updateVisited(userid);
	}


}
