package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.SidoGugunCodeDto;
import com.ssafy.happyhouse.service.HouseMapService;

@RestController
@RequestMapping("/map")
//@CrossOrigin("*")
public class HouseMapController {
	
	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	@Autowired
	private HouseMapService haHouseMapService;
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
//		logger.debug("sido : {}", haHouseMapService.getSido());
		System.out.println("여기 오냐 ?");
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception {
//		logger.debug("gugun : {}", haHouseMapService.getGugunInSido(sido));
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		System.out.println(gugun);
		List<HouseInfoDto> list= haHouseMapService.getDongInGugun(gugun);
		for(HouseInfoDto a : list) {
			System.out.println(a.getDongName());
		}
		
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dong") String dong) throws Exception {
		System.out.println(dong);
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInDong(dong), HttpStatus.OK);
	}
	
	@GetMapping("/apts")
	public ResponseEntity<List<HouseInfoDto>> aptList(@RequestParam("dong") String dong) throws Exception {
		System.out.println(dong);
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.listHouse(dong), HttpStatus.OK);
	}
	
}
