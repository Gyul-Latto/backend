package com.ssafy.home.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssafy.home.dto.views.ViewsDto;
import com.ssafy.home.repository.RedisViewCountRepository;
import com.ssafy.home.repository.ViewCountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViewCountService {
	private final RedisViewCountRepository redisViewCountRepository;
	private final ViewCountRepository viewCountRepository;

	/**
	 * 1시간마다 실행: 1시간 단위 조회수를 일간 조회수에 합산
	 */
	// @Scheduled(cron = "0 0 * * * *")  // 매 정각에 실행
	@Scheduled(cron = "*/10 * * * * *") // test 용 10초마다 실행
	public void processHourlyToDailyViewCounts() {
		log.info("[ 1시간 조회수 ==> 일간 조회수에 합산 시작 ]");
		String timeSlot = getCurrentTimeSlot(); // ex) "2024-11-25:00-01"
		String hourlyKey = "apartment:views:hour:" + timeSlot;
		String dailyKey = "apartment:views:daily:" + timeSlot.split(":")[0];

		// 1시간 단위 조회수를 일간 조회수에 합산
		redisViewCountRepository.mergeHourlyToDaily(hourlyKey, dailyKey);

		log.info("==== 일간 조회수에 합산 완료 ====");

		// 1시간 단위 조회수 DB에 저장
		log.info("[ 1시간 조회수를 DB Insert 시작 ]");
		// Redis에서 value와 score 가져오기
		Map<String, Double> viewCounts = redisViewCountRepository.getZSetValueAndScores(hourlyKey);

		// 데이터베이스에 저장
		viewCounts.forEach((aptSeq, views) -> {
			ViewsDto viewsDto = ViewsDto.builder()
				.aptSeq(aptSeq)
				.viewCount(views.longValue())
				.date(timeSlot.split(":")[0])
				.hour(timeSlot.split(":")[1])
				.build();
			viewCountRepository.saveViewCount(viewsDto);
		});
		log.info("==== 1시간 조회수를 DB Insert 완료 ====");

		// Redis에서 1시간 단위 조회수 삭제
		// redisViewCountRepository.deleteKey(hourlyKey);
		// log.info("Redis 1시간 조회수 삭제");
	}

	/**
	 * 매일 00시마다 실행: 1일 단위 조회수를 DB에 삽입
	 */
	// @Scheduled(cron = "0 0 0 * * *")  // 매일 자정에 실행
	@Scheduled(cron = "*/30 * * * * *") // test용 30초마다 실행
	public void processDailyViewCounts() {

		log.info("[ 일간 조회수 ==> DB에 삽입 시작 ]");
		String timeSlot = getCurrentTimeSlot(); // ex) "2024-11-25:00-01"
		String dailyKey = "apartment:views:daily:" + timeSlot.split(":")[0]; // ex) "2024-11-25"

		// Redis에서 value와 score 가져오기
		Map<String, Double> viewCounts = redisViewCountRepository.getZSetValueAndScores(dailyKey);

		// 일간 조회수를 DB에 삽입
		viewCounts.forEach((aptSeq, views) -> {
			ViewsDto viewsDto = ViewsDto.builder()
				.aptSeq(aptSeq)
				.viewCount(views.longValue())
				.date(timeSlot.split(":")[0])
				.build();
			viewCountRepository.saveDailyViews(viewsDto);
		});
		log.info("==== 일간 조회수를 DB Insert 완료 ====");

		// Redis에서 일간 조회수 삭제
		// redisViewCountRepository.deleteKey(dailyKey);
		// log.info("Redis 일간 조회수 삭제");
	}

	/**
	 * 현재 시간대를 가져옴
	 * @return 현재 시간대 (예: "2024-11-25:00-01")
	 */
	private String getCurrentTimeSlot() {
		LocalDateTime now = LocalDateTime.now();
		int hour = now.getHour();
		String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return String.format("%s:%02d-%02d", date, hour, hour + 1);
	}

	public void updateViewCount(String aptSeq) {
		redisViewCountRepository.updateHourlyViewCount(aptSeq, getCurrentTimeSlot());
	}

}
