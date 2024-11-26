package com.ssafy.home.dto.views;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DailyViewsDto {
	private String aptSeq;
	private Long viewCount;
	private String date;
	private String hour;
}
