package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.Reply;
import com.ssafy.happyhouse.dto.Community;

public interface CommService {
	public List<Community> retrieveCommunity();
	public Community detailCommunity(int communityno);
	public boolean writeCommunity(Community community);
	public boolean updateCommunity(Community community);
	public boolean deleteCommunity(int communityno);
	
	public List<Reply> retrieveReply(int communityno);
	public Reply detailReply(int replyno);
	public boolean writeReply(Reply reply);
	public boolean updateReply(Reply reply);
	public boolean deleteReply(int replyno);
}
