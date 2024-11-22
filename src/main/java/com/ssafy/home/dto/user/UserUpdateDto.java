package com.ssafy.home.dto.user;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserUpdateDto {
	private Long userId;
	private String username;
	private String password;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	private int gender;
}
