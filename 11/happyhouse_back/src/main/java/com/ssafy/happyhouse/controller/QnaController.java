package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.Answer;
import com.ssafy.happyhouse.dto.Question;
import com.ssafy.happyhouse.service.QnaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("QnaController V1")
@RestController
@RequestMapping("/qna")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class QnaController {
	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private QnaService qnaService;

	//질문
    @ApiOperation(value = "Q&A 글목록", notes = "모든 질문의 정보를 반환한다.", response = List.class)
	@GetMapping("question")
	public ResponseEntity<List<Question>> retrieveQuestion() throws Exception {
		logger.debug("retrieveQuestion - 호출");
		return new ResponseEntity<List<Question>>(qnaService.retrieveQuestion(), HttpStatus.OK);
	}

    @ApiOperation(value = "Q&A 글보기", notes = "글번호에 해당하는 질문의 정보를 반환한다.", response = Question.class)    
	@GetMapping("question/{questionno}")
	public ResponseEntity<Question> detailQuestion(@PathVariable int questionno) {
		logger.debug("detailQuestion - 호출");
		return new ResponseEntity<Question>(qnaService.detailQuestion(questionno), HttpStatus.OK);
	}

    @ApiOperation(value = "Q&A 글등록", notes = "새로운 질문 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("question")
	public ResponseEntity<String> writeQuestion(@RequestBody Question question) {
		logger.debug("writeQuestion - 호출");
		if (qnaService.writeQuestion(question)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "Q&A 글정보 수정", notes = "글번호에 해당하는 질문의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("question/{questionno}")
	public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
		logger.debug("updateQuestion - 호출");
		
		if (qnaService.updateQuestion(question)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "Q&A 글삭제", notes = "글번호에 해당하는 질문의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("question/{questionno}")
	public ResponseEntity<String> deleteQuestion(@PathVariable int questionno) {
		logger.debug("deleteQuestion - 호출");
		if (qnaService.deleteQuestion(questionno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
    
    //답변
    @ApiOperation(value = "Q&A 글목록", notes = "해당 질문의 모든 답변 정보를 반환한다.", response = List.class)
	@GetMapping("answer/q/{questionno}")
	public ResponseEntity<List<Answer>> retrieveAnswer(@PathVariable int questionno) throws Exception {
		logger.debug("retrieveAnswer - 호출");
		return new ResponseEntity<List<Answer>>(qnaService.retrieveAnswer(questionno), HttpStatus.OK);
	}

    @ApiOperation(value = "Q&A 글보기", notes = "글번호에 해당하는 질문의 정보를 반환한다.", response = Answer.class)    
	@GetMapping("answer/{answerno}")
	public ResponseEntity<Answer> detailAnswer(@PathVariable int answerno) {
		logger.debug("detailAnswer - 호출");
		return new ResponseEntity<Answer>(qnaService.detailAnswer(answerno), HttpStatus.OK);
	}

    @ApiOperation(value = "Q&A 글등록", notes = "새로운 질문 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("answer")
	public ResponseEntity<String> writeAnswer(@RequestBody Answer answer) {
		logger.debug("writeAnswer - 호출");
		if (qnaService.writeAnswer(answer)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "Q&A 글정보 수정", notes = "글번호에 해당하는 질문의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("answer/{answerno}")
	public ResponseEntity<String> updateAnswer(@RequestBody Answer answer) {
		logger.debug("updateAnswer - 호출");
		if (qnaService.updateAnswer(answer)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "Q&A 글삭제", notes = "글번호에 해당하는 질문의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("answer/{answerno}")
	public ResponseEntity<String> deleteAnswer(@PathVariable int answerno) {
		logger.debug("deleteAnswer - 호출");
		if (qnaService.deleteAnswer(answerno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
