package com.ssafy.home.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.home.common.response.ErrorCode;
import com.ssafy.home.common.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalException {
	/**
	 * 기타 정의되지 않은 예외는 서버 에러로 처리
	 * 에러 추가 예정
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handle(Exception e) {
		log.error("Exception: {} ", e.getMessage());
		return createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
	}

	// 공용 메서드
	private ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode) {
		return new ResponseEntity<>(
			ErrorResponse.of(errorCode),
			errorCode.getStatus()
		);
	}
}
