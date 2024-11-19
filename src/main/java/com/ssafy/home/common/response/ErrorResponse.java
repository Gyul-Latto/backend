package com.ssafy.home.common.response;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private int statusCode;
	private String code;
	private String message;

	private ErrorResponse(final ErrorCode errorCode) {
		this.statusCode = errorCode.getStatusCode();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	private ErrorResponse(final int statusCode, final String code, final String message) {
		this.statusCode = statusCode;
		this.code = code;
		this.message = message;
	}

	public static ErrorResponse of(final ErrorCode code) {
		return new ErrorResponse(code);
	}

	public static ErrorResponse of(final HttpStatus status, final String code, final String message) {
		return new ErrorResponse(status.value(), code, message);
	}

	public static ErrorResponse of(final int statusCode, final String message) {
		return new ErrorResponse(statusCode, "UNKNOWN", message);
	}
}
