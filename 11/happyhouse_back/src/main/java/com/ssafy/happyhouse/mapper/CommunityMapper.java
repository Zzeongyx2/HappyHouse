package com.ssafy.happyhouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.Community;

@Mapper
public interface CommunityMapper {
	public List<Community> selectCommunity();
	public Community selectCommunityByNo(int communityno);
	public int insertCommunity(Community community);
	public int updateCommunity(Community community);
	public int deleteCommunity(int communityno);
}
