package com.ssafy.home.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.ssafy.home.common.response.ErrorCode;
import com.ssafy.home.common.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * HTTP 요청에서 지원되지 않는 HTTP 메서드가 사용된 경우 발생하는 예외를 처리합니다.
	 * 이 예외는 클라이언트가 요청한 엔드포인트에 대해 잘못된 HTTP 메서드(예: GET 대신 POST)를 사용했을 때 발생합니다.
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handle(HttpRequestMethodNotSupportedException e) {
		log.error("HttpRequestMethodNotSupportedException", e);
		return createErrorResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
	}

	/**
	 * 요청한 리소스를 서버에서 찾을 수 없을 경우 발생하는 예외를 처리합니다.
	 * 이는 클라이언트가 존재하지 않는 URL이나 데이터에 접근하려고 했을 때 발생합니다.
	 */
	@ExceptionHandler(NoResourceFoundException.class)
	protected ResponseEntity<ErrorResponse> handle(NoResourceFoundException e) {
		log.error("NoResourceFoundException", e);
		return createErrorResponseEntity(ErrorCode.RESOURCE_NOT_FOUND);
	}

	/**
	 * 이 메서드는 CustomException과 그 하위 예외들이 발생했을 때 호출됩니다.
	 * CustomException을 상속한 다양한 예외들에 대해 공통적으로 처리할 수 있도록 설계되었습니다.
	 */
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handle(CustomException e) {
		log.error("CustomException", e);
		return createErrorResponseEntity(e.getErrorCode());
	}

	/**
	 * 기타 정의되지 않은 예외는 서버 에러로 처리
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handle(Exception e) {
		log.error("Exception", e);
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
