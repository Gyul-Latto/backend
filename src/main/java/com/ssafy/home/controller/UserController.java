package com.ssafy.home.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.SuccessResponse;
import com.ssafy.home.dto.user.SignUpRequestDto;
import com.ssafy.home.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public SuccessResponse<Boolean> createUser(@RequestBody SignUpRequestDto signUpRequestDto) {
		return SuccessResponse.of(userService.insertUser(signUpRequestDto));
	}

	@GetMapping("/test")
	public SuccessResponse<String> test() {
		return SuccessResponse.of("test");
	}

}
