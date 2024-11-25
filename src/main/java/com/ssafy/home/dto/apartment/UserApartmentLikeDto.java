package com.ssafy.home.dto.apartment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserApartmentLikeDto {
	private int userApartmentLikeId;
	private int userId;
	private String aptSeq;
}
