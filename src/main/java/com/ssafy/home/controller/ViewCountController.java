package com.ssafy.home.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.SuccessResponse;
import com.ssafy.home.dto.apartment.ApartmentDetailDto;
import com.ssafy.home.service.ApartmentService;
import com.ssafy.home.service.ViewCountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/views")
@RequiredArgsConstructor
@Slf4j
public class ViewCountController {
	private final ViewCountService viewCountService;
	private final ApartmentService apartmentService;

	// 아파트 시퀀스 (aptSeq) 업데이트
	@GetMapping("/apt/{aptSeq}")
	public void updateViewCount(@PathVariable String aptSeq) {
		viewCountService.updateViewCount(aptSeq);
	}

	// 날짜와 시간별 조회
	@GetMapping("/date/{date}/hour/{hour}")
	public SuccessResponse<List<ApartmentDetailDto>> getTopHourViews(
		@PathVariable String date,
		@PathVariable int hour,
		@RequestParam(defaultValue = "6") int limit
	) {
		List<String> aptSeqList = viewCountService.getTopHourViews(date, hour, limit);
		return SuccessResponse.of(apartmentService.getApartmentsByAptSeqList(aptSeqList));
	}

	// 날짜별 조회
	@GetMapping("/date/{date}")
	public SuccessResponse<List<ApartmentDetailDto>> getTopDailyViews(
		@PathVariable String date,
		@RequestParam(defaultValue = "6") int limit
	) {
		log.info("date: {}", date);
		List<String> aptSeqList = viewCountService.getTopDailyViews(date, limit);
		return SuccessResponse.of(apartmentService.getApartmentsByAptSeqList(aptSeqList));
	}
}