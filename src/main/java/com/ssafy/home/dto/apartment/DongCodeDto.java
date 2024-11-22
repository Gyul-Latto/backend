package com.ssafy.home.dto.apartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DongCodeDto {
	private String dongCode;
	private String sidoName;
	private String gugunName;
	private String dongName;
}
