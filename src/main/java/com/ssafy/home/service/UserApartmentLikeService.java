package com.ssafy.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.dto.apartment.ApartmentDetailDto;
import com.ssafy.home.dto.apartment.UserApartmentLikeDto;
import com.ssafy.home.repository.UserApartmentLikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserApartmentLikeService {
	private final UserApartmentLikeRepository likeRepository;

	// 좋아요 추가
	public void addLike(int userId, String aptSeq) {
		if (likeRepository.isLikedByUser(userId, aptSeq)) {
			throw new IllegalStateException("이미 좋아요를 누른 아파트입니다.");
		}
		UserApartmentLikeDto like = UserApartmentLikeDto.builder()
			.userId(userId)
			.aptSeq(aptSeq)
			.build();
		likeRepository.addLike(like);
	}

	// 좋아요 제거
	public void removeLike(int userId, String aptSeq) {
		if (!likeRepository.isLikedByUser(userId, aptSeq)) {
			throw new IllegalStateException("좋아요를 누르지 않은 아파트입니다.");
		}
		UserApartmentLikeDto like = UserApartmentLikeDto.builder()
			.userId(userId)
			.aptSeq(aptSeq)
			.build();
		likeRepository.removeLike(like);
	}

	// 사용자가 좋아요한 아파트 목록 조회
	public List<ApartmentDetailDto> findLikesByUserId(int userId) {
		return likeRepository.findLikesByUserId(userId);
	}

	// 특정 아파트 좋아요 상태 확인
	public boolean isLikedByUser(int userId, String aptSeq) {
		return likeRepository.isLikedByUser(userId, aptSeq);
	}
}
