package com.ssafy.home.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.home.dto.apartment.ApartmentDto;
import com.ssafy.home.dto.apartment.DongCodeDto;

@Mapper
public interface ApartmentRepository {

	// 동 코드로 아파트 리스트 조회
	List<ApartmentDto> findApartmentsByDongCode(@Param("dongCode") String dongCode);

	// 시/구/동 이름으로 동 코드 조회
	DongCodeDto findDongCode(
		@Param("sidoName") String sidoName,
		@Param("gugunName") String gugunName,
		@Param("dongName") String dongName
	);
}
