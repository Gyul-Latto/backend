package com.ssafy.home.dto.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApartmentInfoDto {
	private int apartmentInfoId;
	private String aptSeq;
	private String aptImg;
	private String excluUseAr;
	private String floor;
	private String description;
}
