package com.ssafy.home.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.home.common.response.SuccessResponse;
import com.ssafy.home.dto.apartment.ApartmentDetailDto;
import com.ssafy.home.service.ApartmentService;
import com.ssafy.home.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendationController {

	private final RecommendationService recommendationService;
	private final ApartmentService apartmentService;

	@Value("${jwt.secret}")
	private String jwtSecret;

	@GetMapping
	public SuccessResponse<List<ApartmentDetailDto>> recommendApartments(
		@RequestHeader("Authorization") String token,
		@RequestParam String recommendationType) {

		try {
			String jwt = token.replace("Bearer ", "").trim();
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret)) // `jwt.secret` 사용
				.build()
				.verify(jwt);

			// userId 추출
			int userId = decodedJWT.getClaim("userId").asInt();

			// FastAPI에서 추천 결과 가져오기
			List<String> aptSeqList = recommendationService.getRecommendations(userId, recommendationType).stream()
				.map(result -> (String)result.get("apt_seq"))
				.collect(Collectors.toList());

			// 추천된 아파트 리스트 조회
			List<ApartmentDetailDto> apartments = apartmentService.getApartmentsByAptSeqList(aptSeqList);

			return SuccessResponse.of(apartments);

		} catch (JWTVerificationException e) {
			e.printStackTrace();
			return SuccessResponse.of(401, "JWT 검증 실패: " + e.getMessage(), null);
		} catch (Exception e) {
			e.printStackTrace();
			return SuccessResponse.of(500, "추천 시스템 호출 실패: " + e.getMessage(), null);
		}
	}
}