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

import com.ssafy.happyhouse.dto.Reply;
import com.ssafy.happyhouse.dto.Community;
import com.ssafy.happyhouse.service.CommService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("CommController V1")
@RestController
@RequestMapping("/comm")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class CommunityController {
	private static final Logger logger = LoggerFactory.getLogger(CommunityController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private CommService commService;

	//질문
    @ApiOperation(value = "Q&A 글목록", notes = "모든 질문의 정보를 반환한다.", response = List.class)
	@GetMapping("community")
	public ResponseEntity<List<Community>> retrieveCommunity() throws Exception {
		logger.debug("retrieveCommunity - 호출");
		return new ResponseEntity<List<Community>>(commService.retrieveCommunity(), HttpStatus.OK);
	}

    @ApiOperation(value = "Q&A 글보기", notes = "글번호에 해당하는 질문의 정보를 반환한다.", response = Community.class)    
	@GetMapping("community/{communityno}")
	public ResponseEntity<Community> detailCommunity(@PathVariable int communityno) {
		logger.debug("detailCommunity - 호출");
		return new ResponseEntity<Community>(commService.detailCommunity(communityno), HttpStatus.OK);
	}

    @ApiOperation(value = "Q&A 글등록", notes = "새로운 질문 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("community")
	public ResponseEntity<String> writeCommunity(@RequestBody Community community) {
		logger.debug("writeCommunity - 호출");
		if (commService.writeCommunity(community)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "Q&A 글정보 수정", notes = "글번호에 해당하는 질문의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("community/{communityno}")
	public ResponseEntity<String> updateCommunity(@RequestBody Community community) {
		logger.debug("updateCommunity - 호출");
		
		if (commService.updateCommunity(community)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "Q&A 글삭제", notes = "글번호에 해당하는 질문의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("community/{communityno}")
	public ResponseEntity<String> deleteCommunity(@PathVariable int communityno) {
		logger.debug("deleteCommunity - 호출");
		if (commService.deleteCommunity(communityno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
    
    //답변
    @ApiOperation(value = "Q&A 글목록", notes = "해당 질문의 모든 답변 정보를 반환한다.", response = List.class)
	@GetMapping("reply/c/{communityno}")
	public ResponseEntity<List<Reply>> retrieveReply(@PathVariable int communityno) throws Exception {
		logger.debug("retrieveReply - 호출");
		return new ResponseEntity<List<Reply>>(commService.retrieveReply(communityno), HttpStatus.OK);
	}

    @ApiOperation(value = "Q&A 글보기", notes = "글번호에 해당하는 질문의 정보를 반환한다.", response = Reply.class)    
	@GetMapping("reply/{replyno}")
	public ResponseEntity<Reply> detailReply(@PathVariable int replyno) {
		logger.debug("detailReply - 호출");
		return new ResponseEntity<Reply>(commService.detailReply(replyno), HttpStatus.OK);
	}

    @ApiOperation(value = "Q&A 글등록", notes = "새로운 질문 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("reply")
	public ResponseEntity<String> writeReply(@RequestBody Reply reply) {
		logger.debug("writeReply - 호출");
		if (commService.writeReply(reply)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "Q&A 글정보 수정", notes = "글번호에 해당하는 질문의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("reply/{replyno}")
	public ResponseEntity<String> updateReply(@RequestBody Reply reply) {
		logger.debug("updateReply - 호출");
		if (commService.updateReply(reply)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "Q&A 글삭제", notes = "글번호에 해당하는 질문의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("reply/{replyno}")
	public ResponseEntity<String> deleteReply(@PathVariable int replyno) {
		logger.debug("deleteReply - 호출");
		if (commService.deleteReply(replyno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
