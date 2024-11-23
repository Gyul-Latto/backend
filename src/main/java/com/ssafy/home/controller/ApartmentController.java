package com.ssafy.home.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.SuccessResponse;
import com.ssafy.home.dto.apartment.ApartmentDetailDto;
import com.ssafy.home.dto.apartment.DongCodeDto;
import com.ssafy.home.service.ApartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApartmentController {

	private final ApartmentService apartmentService;

	// 시/구/동 정보로 아파트 리스트 조회
	@PostMapping("/apt/search/sido")
	public SuccessResponse<List<ApartmentDetailDto>> searchApartments(@RequestBody DongCodeDto dongCodeDto) {

		List<ApartmentDetailDto> apartments = apartmentService.searchApartments(
			dongCodeDto.getSidoName(),
			dongCodeDto.getGugunName(),
			dongCodeDto.getDongName()
		);
		return SuccessResponse.of(apartments);
	}

	// 아파트 이름으로 검색
	@GetMapping("/apt/search/name")
	public SuccessResponse<List<ApartmentDetailDto>> searchApartmentByName(
		@RequestParam(value = "aptName", required = false) String aptName) {
		if (aptName == null || aptName.isBlank()) {
			return SuccessResponse.of(List.of());
		}
		List<ApartmentDetailDto> apartments = apartmentService.searchApartmentsByName(aptName);
		return SuccessResponse.of(apartments);
	}

}

