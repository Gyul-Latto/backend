package com.ssafy.home.common.exception.exceptions;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.response.ErrorCode;

public class BadRequestException extends CustomException {

	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}
}
