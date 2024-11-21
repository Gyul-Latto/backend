package com.ssafy.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RecommendationService {

	private static final String FASTAPI_URL = "http://13.125.83.30:8000/recommend";
	private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

	public List<Map<String, Object>> getRecommendations(int userId, String recommendationType) {

		Map<String, Object> request = new HashMap<>();
		request.put("user_id", userId); // 나중에 로그인된 사용자를 입력
		request.put("recommendation_type", recommendationType);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

		try {
			logger.info("Calling FastAPI with userId: {}, recommendationType: {}", userId, recommendationType);
			return restTemplate.postForObject(FASTAPI_URL, entity, List.class);
		} catch (RestClientException e) {
			logger.error("FastAPI call failed: {}", e.getMessage());
			throw new RuntimeException("추천 시스템 호출 실패", e);
		} catch (Exception e) {
			logger.error("Unexpected error during FastAPI call: {}", e.getMessage());
			throw new RuntimeException("예기치 못한 오류 발생", e);
		}
	}
}
