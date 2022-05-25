package com.ssafy.happyhouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.Reply;

@Mapper
public interface ReplyMapper {
	public List<Reply> selectReply(int questionno);
	public Reply selectReplyByNo(int replyno);
	public int insertReply(Reply reply);
	public int updateReply(Reply reply);
	public int deleteReply(int replyno);
}
