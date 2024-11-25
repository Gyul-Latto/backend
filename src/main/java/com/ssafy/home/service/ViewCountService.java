package com.ssafy.home.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewCountService {
	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * 1시간 단위 조회수 업데이트
	 * @param aptSeq 아파트 ID
	 * @param timeSlot 시간대 (예: "00-01")
	 */
	public void updateHourlyViewCount(String aptSeq, String timeSlot) {
		String key = "apartment:views:" + timeSlot;
		// 조회수 1 증가 또는 새로 추가
		redisTemplate.opsForZSet().incrementScore(key, aptSeq, 1);
	}

	/**
	 * 1시간 단위 조회수를 일간 조회수에 합산
	 * @param hourlyKey 1시간 단위 조회수 Sorted Set의 키 (예: apartment:views:2024-11-25:00-01)
	 * @param dailyKey  일간 조회수 Sorted Set의 키 (예: apartment:views:2024-11-25)
	 */
	public void mergeHourlyToDaily(String hourlyKey, String dailyKey) {
		// 1시간 단위 조회수 데이터 가져오기
		Set<String> aptSeqs = redisTemplate.opsForZSet().range(hourlyKey, 0, -1);

		if (aptSeqs != null) {
			for (String aptSeq : aptSeqs) {
				// 아파트 seq의 조회수를 가져옴
				Double score = redisTemplate.opsForZSet().score(hourlyKey, aptSeq);
				if (score != null) {
					// 일간 조회수 Sorted Set에 누적
					redisTemplate.opsForZSet().incrementScore(dailyKey, aptSeq, score);
				}
			}
		}
	}

	/**
	 * Redis에서 특정 키 삭제
	 * @param key 삭제할 Redis 키
	 */
	public void deleteRedisKey(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 상위 10개 조회수가 많은 아파트를 반환
	 * @param key 조회할 Redis Key (예: "apartment:views:00-01" 또는 "apartment:views:2024-11-25")
	 * @return 상위 10개 aptSeq 리스트
	 */
	public List<String> getTop10Apartments(String key) {
		// 상위 10개 조회수 아파트 (조회수가 높은 순)
		Set<String> topApartments = redisTemplate.opsForZSet().reverseRange(key, 0, 9);
		// 아파트가 없으면 빈 리스트 반환
		return new ArrayList<>(topApartments);
	}

	private String getCurrentTimeSlot() {
		LocalDateTime now = LocalDateTime.now();
		int hour = now.getHour();
		String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return String.format("%s:%02d-%02d", date, hour, hour + 1);
	}

}
