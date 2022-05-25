package com.ssafy.happyhouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.Answer;

@Mapper
public interface AnswerMapper {
	public List<Answer> selectAnswer(int questionno);
	public Answer selectAnswerByNo(int answerno);
	public int insertAnswer(Answer answer);
	public int updateAnswer(Answer answer);
	public int deleteAnswer(int answerno);
}
