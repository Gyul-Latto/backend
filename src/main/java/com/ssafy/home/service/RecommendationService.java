package com.ssafy.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecommendationService {

	private static final String FASTAPI_URL = "http://13.125.83.30:8000/recommend";

	public List<Map<String, Object>> getRecommendations(int userId, String recommendationType) {

		Map<String, Object> request = new HashMap<>();
		request.put("user_id", userId);
		request.put("recommendation_type", recommendationType);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

		try {
			ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
				FASTAPI_URL,
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<List<Map<String, Object>>>() {
				}
			);

			return response.getBody();
		} catch (RestClientException e) {
			log.error("FastAPI call failed: {}", e.getMessage());
			throw new RuntimeException("추천 시스템 호출 실패", e);
		}
	}
}
