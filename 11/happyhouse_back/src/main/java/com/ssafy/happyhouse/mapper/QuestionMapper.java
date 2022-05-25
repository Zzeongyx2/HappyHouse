package com.ssafy.happyhouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.Question;

@Mapper
public interface QuestionMapper {
	public List<Question> selectQuestion();
	public Question selectQuestionByNo(int questionno);
	public int insertQuestion(Question question);
	public int updateQuestion(Question question);
	public int deleteQuestion(int questionno);
}
