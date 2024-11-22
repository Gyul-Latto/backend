package com.ssafy.home.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.home.common.exception.exceptions.BadRequestException;
import com.ssafy.home.common.response.ErrorCode;
import com.ssafy.home.dto.user.SignUpRequestDto;
import com.ssafy.home.dto.user.UserInfoDto;
import com.ssafy.home.dto.user.UserUpdateDto;
import com.ssafy.home.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	// 회원조회
	public UserInfoDto getUsers(Long tokenUserId, Long urlUserId) {
		// tokenUserId와 urlUserId가 다르면 Exception 발생
		if (!tokenUserId.equals(urlUserId)) {
			throw new BadRequestException(ErrorCode.FORBIDDEN);
		}
		// urlUserId로 사용자 조회, 없으면 Exception 발생, 있으면 UserInfoDto로 변환해서 반환
		return userRepository.findByUserId(urlUserId).map(user -> UserInfoDto.builder()
			.userId(user.getUserId())
			.email(user.getEmail())
			.username(user.getUsername())
			.birthday(user.getBirthday())
			.gender(user.getGender())
			.build()
		).orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));
	}

	// 회원가입
	public boolean insertUser(SignUpRequestDto signUpRequestDto) {

		// 이메일 중복 체크
		userRepository.findByEmail(signUpRequestDto.getEmail())
			.ifPresent(user -> {
				throw new BadRequestException(ErrorCode.DUPLICATE_EMAIL);
			});

		// 비밀번호 암호화
		signUpRequestDto.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));

		return userRepository.insertUser(signUpRequestDto);
	}

	// 회원수정
	public boolean updateUsers(Long tokenUserId, Long urlUserId, UserUpdateDto userUpdateDto) {
		// tokenUserId와 urlUserId가 다르면 Exception 발생
		if (!tokenUserId.equals(urlUserId)) {
			throw new BadRequestException(ErrorCode.FORBIDDEN);
		}

		// urlUserId로 사용자 조회, 없으면 Exception 발생
		userRepository.findByUserId(urlUserId).orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));

		// userId 설정
		userUpdateDto.setUserId(tokenUserId);
		// 비밀번호 암호화
		userUpdateDto.setPassword(bCryptPasswordEncoder.encode(userUpdateDto.getPassword()));

		// 사용자 정보 수정 성공하면 true 반환
		return userRepository.updateUser(userUpdateDto) > 0;
	}

	// 회원탈퇴
	public boolean deleteUsers(Long tokenUserId, Long urlUserId) {
		// tokenUserId와 urlUserId가 다르면 Exception 발생
		if (!tokenUserId.equals(urlUserId)) {
			throw new BadRequestException(ErrorCode.FORBIDDEN);
		}
		// urlUserId로 사용자 삭제 성공하면 true 반환
		return userRepository.deleteUser(urlUserId) > 0;
	}
}
