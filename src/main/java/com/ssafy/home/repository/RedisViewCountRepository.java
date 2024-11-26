package com.ssafy.home.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RedisViewCountRepository {
	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * 1시간 단위 조회수 업데이트
	 * @param aptSeq 아파트 ID
	 * @param timeSlot 시간대 (예: "00-01")
	 */
	public void updateHourlyViewCount(String aptSeq, String timeSlot) {
		// 현재 날짜를 "yyyy-MM-dd" 형식으로 가져오기
		String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String key = "apartment:views:hour:" + timeSlot;
		log.info("Time: {} -> {} 아파트 조회수 증가", key, aptSeq);
		// 조회수 1 증가 또는 새로 추가
		redisTemplate.opsForZSet().incrementScore(key, aptSeq, 1);
	}

	/**
	 * 1시간 단위 조회수를 일간 조회수에 합산
	 * @param hourlyKey 1시간 단위 조회수 Sorted Set의 키 (예: apartment:views:2024-11-25:00-01)
	 * @param dailyKey  일간 조회수 Sorted Set의 키 (예: apartment:views:daily:2024-11-25)
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
	public void deleteKey(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 상위 N개 조회수가 많은 아파트를 반환
	 * @param key 조회할 Redis Key (예: "apartment:views:2024-11-25:00-01" 또는 "apartment:views:daily:2024-11-25")
	 * @param topN 반환할 상위 N개 아파트의 개수
	 * @return 상위 N개 aptSeq 리스트
	 */
	public List<String> getTopNApartments(String key, int topN) {
		// 상위 N개 조회수 아파트 (조회수가 높은 순)
		Set<String> topApartments = redisTemplate.opsForZSet().reverseRange(key, 0, topN - 1);

		// 아파트가 없으면 빈 리스트 반환
		return topApartments == null ? new ArrayList<>() : new ArrayList<>(topApartments);
	}

	/**
	 * Redis ZSet에서 value와 score를 모두 가져옵니다.
	 * @param key ZSet의 Redis 키
	 * @return value와 score를 매핑한 Map<String, Double>
	 */
	public Map<String, Double> getZSetValueAndScores(String key) {
		Set<ZSetOperations.TypedTuple<String>> tuples = redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
		Map<String, Double> result = new HashMap<>();

		if (tuples != null) {
			for (ZSetOperations.TypedTuple<String> tuple : tuples) {
				Object value = tuple.getValue();
				Double score = tuple.getScore();
				if (value != null && score != null) {
					result.put(value.toString(), score);
				}
			}
		}

		return result;
	}

}
