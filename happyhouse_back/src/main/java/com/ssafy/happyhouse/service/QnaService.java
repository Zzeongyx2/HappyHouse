package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.Answer;
import com.ssafy.happyhouse.dto.Question;

public interface QnaService {
	public List<Question> retrieveQuestion();
	public Question detailQuestion(int questionno);
	public boolean writeQuestion(Question question);
	public boolean updateQuestion(Question question);
	public boolean deleteQuestion(int questionno);
	
	public List<Answer> retrieveAnswer(int questionno);
	public Answer detailAnswer(int answerno);
	public boolean writeAnswer(Answer answer);
	public boolean updateAnswer(Answer answer);
	public boolean deleteAnswer(int answerno);
}
