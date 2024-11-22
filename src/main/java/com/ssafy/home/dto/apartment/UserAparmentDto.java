package com.ssafy.home.dto.apartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserAparmentDto {
	private int userApartmentLikeId;
	private int userId;
	private String aptSeq;
}
