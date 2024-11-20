package com.ssafy.home.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.dto.user.SignUpRequestDto;
import com.ssafy.home.dto.user.UserDto;

@Mapper
public interface UserRepository {
	// 회원 가입
	boolean insertUser(SignUpRequestDto signUpRequestDto);

	Optional<UserDto> findByEmail(String email);
}
