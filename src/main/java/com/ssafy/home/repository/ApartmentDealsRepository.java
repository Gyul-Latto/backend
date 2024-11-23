package com.ssafy.home.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.home.dto.apartment.ApartmentDealsDto;

@Mapper
public interface ApartmentDealsRepository {
	List<ApartmentDealsDto> findDealsByApartmentSeq(@Param("aptSeq") String aptSeq);
}
