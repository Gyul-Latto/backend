package com.ssafy.home.dto.user;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
	private String username;
	private String password;
	private String email;
	private Date birthday; // 생년월일
	private int gender; // 0: 남자, 1: 여자, 2: 선택안함
	private String dongCode; // 동 코드
}
