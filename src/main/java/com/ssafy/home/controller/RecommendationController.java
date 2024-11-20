package com.ssafy.home.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.SuccessResponse;
import com.ssafy.home.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendationController {
	private final RecommendationService recommendationService;

	@GetMapping
	public SuccessResponse<List<Map<String, Object>>> recommendApartments(
		@RequestParam int userId,
		@RequestParam String recommendationType) {
		try {
			List<Map<String, Object>> recommendations = recommendationService.getRecommendations(userId,
				recommendationType);
			return SuccessResponse.of(recommendations);
		} catch (Exception e) {
			return SuccessResponse.of(500, "추천 시스템 호출 실패: " + e.getMessage(), null);
		}
	}
}
