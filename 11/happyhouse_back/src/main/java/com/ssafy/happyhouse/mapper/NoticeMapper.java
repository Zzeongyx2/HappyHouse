package com.ssafy.happyhouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.Notice;

@Mapper
public interface NoticeMapper {
	public List<Notice> selectNotice();
	public Notice selectNoticeByNo(int articleno);
	public int insertNotice(Notice notice);
	public int updateNotice(Notice notice);
	public int deleteNotice(int articleno);
}
