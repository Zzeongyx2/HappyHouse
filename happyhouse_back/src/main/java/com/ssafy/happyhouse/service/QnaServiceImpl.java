package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.Answer;
import com.ssafy.happyhouse.dto.Question;
import com.ssafy.happyhouse.mapper.AnswerMapper;
import com.ssafy.happyhouse.mapper.QuestionMapper;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private AnswerMapper answerMapper;
	
	
	//질문
	@Override
	public List<Question> retrieveQuestion() {
		return questionMapper.selectQuestion();
	}

	@Override
	public Question detailQuestion(int questionno) {
		return questionMapper.selectQuestionByNo(questionno);
	}

	@Override
	public boolean writeQuestion(Question question) {
		return questionMapper.insertQuestion(question) == 1;
	}

	@Override
	public boolean updateQuestion(Question question) {
		return questionMapper.updateQuestion(question) == 1;
	}

	@Override
	public boolean deleteQuestion(int questionno) {
		return questionMapper.deleteQuestion(questionno) == 1;
	}

	//답변
	@Override
	public List<Answer> retrieveAnswer(int questionno) {
		return answerMapper.selectAnswer(questionno);
	}

	@Override
	public Answer detailAnswer(int answerno) {
		return answerMapper.selectAnswerByNo(answerno);
	}

	@Override
	public boolean writeAnswer(Answer answer) {
		return answerMapper.insertAnswer(answer) == 1;
	}

	@Override
	public boolean updateAnswer(Answer answer) {
		return answerMapper.updateAnswer(answer) == 1;
	}

	@Override
	public boolean deleteAnswer(int answerno) {
		return answerMapper.deleteAnswer(answerno) == 1;
	}
}
