package com.ssafy.home.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuccessResponse<T> {

	private final int statusCode;
	private final String message;
	private final T data;

	//기본
	public static <T> SuccessResponse<T> of(T data) {
		return new SuccessResponse<>(200, "Success", data);
	}

	//커스텀
	public static <T> SuccessResponse<T> of(int statusCode, String message, T data) {
		return new SuccessResponse<>(statusCode, message, data);
	}

}
