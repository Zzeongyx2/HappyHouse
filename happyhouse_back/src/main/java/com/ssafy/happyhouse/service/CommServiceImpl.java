package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.Reply;
import com.ssafy.happyhouse.dto.Community;
import com.ssafy.happyhouse.mapper.ReplyMapper;
import com.ssafy.happyhouse.mapper.CommunityMapper;

@Service
public class CommServiceImpl implements CommService {

	@Autowired
	private CommunityMapper communityMapper;
	@Autowired
	private ReplyMapper replyMapper;
	
	
	//질문
	@Override
	public List<Community> retrieveCommunity() {
		return communityMapper.selectCommunity();
	}

	@Override
	public Community detailCommunity(int communityno) {
		return communityMapper.selectCommunityByNo(communityno);
	}

	@Override
	public boolean writeCommunity(Community community) {
		return communityMapper.insertCommunity(community) == 1;
	}

	@Override
	public boolean updateCommunity(Community community) {
		return communityMapper.updateCommunity(community) == 1;
	}

	@Override
	public boolean deleteCommunity(int communityno) {
		return communityMapper.deleteCommunity(communityno) == 1;
	}

	//답변
	@Override
	public List<Reply> retrieveReply(int communityno) {
		return replyMapper.selectReply(communityno);
	}

	@Override
	public Reply detailReply(int replyno) {
		return replyMapper.selectReplyByNo(replyno);
	}

	@Override
	public boolean writeReply(Reply reply) {
		return replyMapper.insertReply(reply) == 1;
	}

	@Override
	public boolean updateReply(Reply reply) {
		return replyMapper.updateReply(reply) == 1;
	}

	@Override
	public boolean deleteReply(int replyno) {
		return replyMapper.deleteReply(replyno) == 1;
	}
}
