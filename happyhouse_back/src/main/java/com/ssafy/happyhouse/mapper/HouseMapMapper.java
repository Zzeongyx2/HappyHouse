package com.ssafy.happyhouse.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.dto.AptDetailDto;
import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.Region;
import com.ssafy.happyhouse.dto.SidoGugunCodeDto;

public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
	List<HouseInfoDto> listHouse(String dong) throws Exception;
	List<AptDetailDto> aptDetailList(String aptCode) throws Exception;
	int insertRegion(Region region) throws SQLException;
	List<Region> selectRegion(String userid) throws SQLException;
	int deleteRegion(Region region) throws SQLException;
	String selectDongcode(Region region) throws SQLException;
}
