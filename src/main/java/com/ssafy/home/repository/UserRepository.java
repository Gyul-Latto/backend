package com.ssafy.home.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.dto.user.SignUpRequestDto;
import com.ssafy.home.dto.user.UserUpdateDto;
import com.ssafy.home.security.auth.dto.UserDto;

@Mapper
public interface UserRepository {
	// 회원 가입
	boolean insertUser(SignUpRequestDto signUpRequestDto);

	// 이메일로 사용자 조회
	Optional<UserDto> findByEmail(String email);

	// 사용자 ID로 사용자 조회
	Optional<UserDto> findByUserId(Long userId);

	// 사용자 삭제
	int deleteUser(Long urlUserId);

	int updateUser(UserUpdateDto userUpdateDto);
}
