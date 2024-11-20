package com.ssafy.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecommendationService {
	private static final String FASTAPI_URL = "http://13.125.83.30:8000/recommend";

	public List<Map<String, Object>> getRecommendations(int userId, String recommendationType) {
		// 요청 데이터 생성
		Map<String, Object> request = new HashMap<>();
		request.put("user_id", userId);
		request.put("recommendation_type", recommendationType);

		// HTTP 요청 생성
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

		// FastAPI 호출 및 결과 반환
		try {
			return restTemplate.postForObject(FASTAPI_URL, entity, List.class);
		} catch (Exception e) {
			throw new RuntimeException("FastAPI 호출 중 오류 발생: " + e.getMessage());
		}
	}
}
