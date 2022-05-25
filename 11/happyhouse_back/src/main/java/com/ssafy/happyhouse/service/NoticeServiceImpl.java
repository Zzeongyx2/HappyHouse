package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.Notice;
import com.ssafy.happyhouse.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;
	
	@Override
	public List<Notice> retrieveNotice() {
		return noticeMapper.selectNotice();
	}

	@Override
	public Notice detailNotice(int articleno) {
		return noticeMapper.selectNoticeByNo(articleno);
	}

	@Override
	public boolean writeNotice(Notice notice) {
		return noticeMapper.insertNotice(notice) == 1;
	}

	@Override
	public boolean updateNotice(Notice notice) {
		return noticeMapper.updateNotice(notice) == 1;
	}

	@Override
	public boolean deleteNotice(int articleno) {
		return noticeMapper.deleteNotice(articleno) == 1;
	}

}
