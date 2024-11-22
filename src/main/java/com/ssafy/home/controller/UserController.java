package com.ssafy.home.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.SuccessResponse;
import com.ssafy.home.dto.user.SignUpRequestDto;
import com.ssafy.home.dto.user.UserInfoDto;
import com.ssafy.home.dto.user.UserUpdateDto;
import com.ssafy.home.security.auth.dto.CustomUserDetails;
import com.ssafy.home.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;

	// 회원가입
	@PostMapping
	public SuccessResponse<Boolean> createUser(@RequestBody SignUpRequestDto signUpRequestDto) {
		return SuccessResponse.of(userService.insertUser(signUpRequestDto));
	}

	// 회원조회
	@GetMapping("/{userId}")
	public SuccessResponse<UserInfoDto> getUsers(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable("userId") Long urlUserId
	) {
		return SuccessResponse.of(userService.getUsers(userDetails.getUserId(), urlUserId));
	}

	// 회원수정
	@PutMapping("/{userId}")
	public SuccessResponse<Boolean> updateUsers(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable("userId") Long urlUserId,
		@RequestBody UserUpdateDto userUpdateDto
	) {
		return SuccessResponse.of(userService.updateUsers(userDetails.getUserId(), urlUserId, userUpdateDto));
	}

	// 회원탈퇴
	@DeleteMapping("/{userId}")
	public SuccessResponse<Boolean> deleteUsers(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable("userId") Long urlUserId
	) {
		return SuccessResponse.of(userService.deleteUsers(userDetails.getUserId(), urlUserId));
	}

}
