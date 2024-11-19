package com.ssafy.home.common.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "4000", "잘못된 요청입니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "4001", "인증이 필요합니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "4003", "접근이 금지되었습니다."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "4004", "요청하신 리소스를 찾을 수 없습니다."),

	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5001", "서버 에러가 발생했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(final HttpStatus status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public int getStatusCode() {
		return status.value();
	}
}
