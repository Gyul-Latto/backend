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
import com.ssafy.home.dto.apartment.ApartmentDetailDto;
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

	// 사용자가 좋아요한 아파트 보기 (리스트)
	@GetMapping("/{userId}")
	public SuccessResponse<List<ApartmentDetailDto>> findLikesByUserId(@PathVariable int userId) {
		List<ApartmentDetailDto> likes = likeService.findLikesByUserId(userId);
		return SuccessResponse.of(likes);
	}

	// 사용자가 아파트 좋아요 했는지 여부
	@GetMapping
	public SuccessResponse<Boolean> isLikedByUser(@RequestParam int userId, @RequestParam String aptSeq) {
		boolean isLiked = likeService.isLikedByUser(userId, aptSeq);
		return SuccessResponse.of(isLiked);
	}
}
