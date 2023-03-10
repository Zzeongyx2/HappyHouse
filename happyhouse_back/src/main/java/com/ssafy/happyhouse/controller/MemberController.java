package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.MemberDto;
import com.ssafy.happyhouse.service.JwtServiceImpl;
import com.ssafy.happyhouse.service.MemberService;
import com.ssafy.happyhouse.util.Hash;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("MemberController V1")
@RestController
@RequestMapping("/user")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
		@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			
			if (loginUser != null) {
				memberService.updateVisited(loginUser.getUserid());
				String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
				System.out.println(loginUser.toString());
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "사용자 등록", notes = "새로운 사용자 정보를 등록한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/regist")
	public ResponseEntity<Map<String, Object>> resiterUser(@RequestBody MemberDto memberDto) throws Exception {
		logger.debug("registerUser - 호출");
		System.out.println(memberDto.toString());
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			if (memberService.registerUser(memberDto)) {
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("회원가입 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/idcheck/{userid}")
	public ResponseEntity<Integer> idCheck(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid) throws Exception {
		return new ResponseEntity<Integer>(memberService.idCheck(userid), HttpStatus.ACCEPTED);
	}

	@PutMapping("/updateUser")
	public ResponseEntity<Map<String, Object>> updateUser(@RequestBody MemberDto memberDto) throws Exception {
		logger.debug("updateUser - 호출");
		System.out.println(memberDto.toString());
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			if (memberService.updateUser(memberDto)) {
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("회원정보 수정 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PutMapping("/updatePwd")
	public ResponseEntity<Map<String, Object>> updatePwd(@RequestBody MemberDto memberDto) throws Exception {
		logger.debug("updatePwd - 호출");
		System.out.println(memberDto.toString());
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			if (memberService.updatePassword(memberDto)) {
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("비밀번호 수정 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@DeleteMapping("/deleteUser/{userid}")
	public ResponseEntity<Map<String, Object>> deleteUser(
			@PathVariable("userid") @ApiParam(value = "삭제할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
		logger.debug("delete 호출 : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			if (memberService.deleteUser(userid)) {
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("회원 삭제 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/findPwd")
	public ResponseEntity<Map<String, Object>> findPwd(@RequestBody MemberDto memberDto) throws Exception {
		logger.debug("findPassword - 호출");
		System.out.println(memberDto.toString());

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String answer = memberService.findPWD(memberDto);
			
			// 비밀번호 복호화
			Hash hash = new Hash(answer);
			hash.setDecryptData(answer);
			String dec = hash.getDecryptData();
			
			answer = dec;
			
			if (answer.length() > 0) {
				resultMap.put("answer",answer);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("비밀번호 찾기 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/users")
	public ResponseEntity<Map<String, String>> calcData() throws Exception{
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("calcUsers", Integer.toString(memberService.calcUsers()));
		resultMap.put("totalVisited", Integer.toString(memberService.getTotalVisited()));
		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.ACCEPTED);
	}
	
}
