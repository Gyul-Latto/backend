package com.ssafy.home.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.SuccessResponse;
import com.ssafy.home.dto.apartment.UserApartmentLikeDto;
import com.ssafy.home.service.UserApartmentLikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apt/like")
public class UserApartmentLikeController {

	private final UserApartmentLikeService likeService;

	// 좋아요 추가
	@PostMapping
	public SuccessResponse<String> addLike(@RequestParam int userId, @RequestParam String aptSeq) {
		likeService.addLike(userId, aptSeq);
		return SuccessResponse.of("좋아요 추가 완료");
	}

	// 좋아요 제거
	@DeleteMapping
	public SuccessResponse<String> removeLike(@RequestParam int userId, @RequestParam String aptSeq) {
		likeService.removeLike(userId, aptSeq);
		return SuccessResponse.of("좋아요 제거 완료");
	}

	// 사용자가 좋아요한 아파트 보기
	@GetMapping("/{userId}")
	public SuccessResponse<List<UserApartmentLikeDto>> findLikesByUserId(@PathVariable int userId) {
		List<UserApartmentLikeDto> likes = likeService.findLikesByUserId(userId);
		return SuccessResponse.of(likes);
	}
}
