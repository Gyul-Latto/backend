package com.ssafy.home.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.SuccessResponse;
import com.ssafy.home.dto.apartment.ApartmentDto;
import com.ssafy.home.service.ApartmentService;
import com.ssafy.home.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
@CrossOrigin(origins = "http://localhost:5173")
public class RecommendationController {

	private final RecommendationService recommendationService;
	private final ApartmentService apartmentService;

	@GetMapping
	public SuccessResponse<List<ApartmentDto>> recommendApartments(
		@RequestParam int userId,
		@RequestParam String recommendationType) {
		try {
			// FastAPI에서 추천 결과 가져오기
			List<String> aptSeqList = recommendationService.getRecommendations(userId, recommendationType).stream()
				.map(result -> (String)result.get("apt_seq"))
				.collect(Collectors.toList());

			// 추천된 아파트 리스트 조회
			List<ApartmentDto> apartments = apartmentService.getApartmentsByAptSeqList(aptSeqList);

			return SuccessResponse.of(apartments);
		} catch (Exception e) {
			return SuccessResponse.of(500, "추천 시스템 호출 실패: " + e.getMessage(), null);
		}
	}
}
