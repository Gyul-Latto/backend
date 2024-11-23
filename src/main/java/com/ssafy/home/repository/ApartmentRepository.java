package com.ssafy.home.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.home.dto.apartment.ApartmentDetailDto;
import com.ssafy.home.dto.apartment.DongCodeDto;

@Mapper
public interface ApartmentRepository {

	// 동 코드로 아파트 리스트 조회
	List<ApartmentDetailDto> findApartmentsByDongCode(@Param("dongCode") String dongCode);

	// 시/구/동 이름으로 동 코드 조회
	DongCodeDto findDongCode(
		@Param("sidoName") String sidoName,
		@Param("gugunName") String gugunName,
		@Param("dongName") String dongName
	);

	//상세 아파트 불러오기
	List<ApartmentDetailDto> findApartmentsByAptSeqList(@Param("list") List<String> aptSeqList);

	// 아파트 이름으로 검색
	List<ApartmentDetailDto> findApartmentsByName(@Param("aptName") String aptName);
}
