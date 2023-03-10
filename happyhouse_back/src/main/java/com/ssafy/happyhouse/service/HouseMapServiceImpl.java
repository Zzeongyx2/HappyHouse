package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.Region;
import com.ssafy.happyhouse.dto.SidoGugunCodeDto;
import com.ssafy.happyhouse.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private HouseMapMapper houseMapMapper;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return houseMapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return houseMapMapper.getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return houseMapMapper.getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		return houseMapMapper.getAptInDong(dong);
	}

	@Override
	public List<HouseInfoDto> listHouse(String dong) throws Exception {
		return houseMapMapper.listHouse(dong);
	}
	@Override
	public boolean saveRegion(Region region) throws Exception {
		String dongcode = houseMapMapper.selectDongcode(region);
		System.out.println("===== " + dongcode);
		if(dongcode != null) {
			region.setDongcode(dongcode);
		}
		return houseMapMapper.insertRegion(region) == 1;
	}

	@Override
	public List<Region> retrieveRegion(String userid) throws Exception {
		return houseMapMapper.selectRegion(userid);
	}

	@Override
	public boolean deleteRegion(Region region) throws Exception {
		return houseMapMapper.deleteRegion(region) == 1;
	}
	
}
