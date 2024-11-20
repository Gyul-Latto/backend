package com.ssafy.home.dto.recommendation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationRequestDto {
	private int userId;
	private String recommendationType;
}
