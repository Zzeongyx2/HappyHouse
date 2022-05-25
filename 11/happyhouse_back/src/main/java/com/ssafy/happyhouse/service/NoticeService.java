package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.Notice;

public interface NoticeService {
	public List<Notice> retrieveNotice();
	public Notice detailNotice(int articleno);
	public boolean writeNotice(Notice notice);
	public boolean updateNotice(Notice notice);
	public boolean deleteNotice(int articleno);
}
