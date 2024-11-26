package com.ssafy.home.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.dto.apartment.ApartmentDetailDto;
import com.ssafy.home.dto.apartment.UserApartmentLikeDto;

@Mapper
public interface UserApartmentLikeRepository {
	void addLike(UserApartmentLikeDto like); // 좋아요 추가

	void removeLike(UserApartmentLikeDto like); // 좋아요 제거

	List<ApartmentDetailDto> findLikesByUserId(int userId); // 사용자가 좋아요 한 아파트 List

	boolean isLikedByUser(int userId, String aptSeq); // 사용자가 아파트를 좋아했는지 여부
}
