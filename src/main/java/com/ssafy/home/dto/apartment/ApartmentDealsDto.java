package com.ssafy.home.dto.apartment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ApartmentDealsDto {
	private int apartmentDealsId;
	private String aptSeq;
	private String aptDong;
	private String dealFloor;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String dealExcluUseAr;
	private String dealAmount;
}
