package com.ssafy.home.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.home.common.exception.exceptions.BadRequestException;
import com.ssafy.home.common.response.ErrorCode;
import com.ssafy.home.dto.user.SignUpRequestDto;
import com.ssafy.home.dto.user.UserDto;
import com.ssafy.home.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;

	public boolean insertUser(SignUpRequestDto signUpRequestDto) {
		Optional<UserDto> user = userRepository.findByEmail(signUpRequestDto.getEmail());
		// User가 존재하면 BadRequestException 발생
		if (user.isPresent()) {
			throw new BadRequestException(ErrorCode.DUPLICATE_EMAIL);
		}
		return userRepository.insertUser(signUpRequestDto);
	}

}
