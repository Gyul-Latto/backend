package com.ssafy.home.security.auth.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {
	private Long userId;
	private String username;
	private String password;
	private String email;
	@JsonFormat(pattern = "yyyy-MM-dd") // 날짜 포맷 지정
	private LocalDate birthday; // 생년월일
	private int gender; // 0: 남자, 1: 여자, 2: 선택안함
	private String dongCode; // 동 코드
}
