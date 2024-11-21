package com.ssafy.home.dto.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApartmentDto {
	private String aptSeq;
	private String sggCd;
	private String umdCd;
	private String umdNm;
	private String jibun;
	private String roadNmSggCd;
	private String roadNm;
	private String roadNmBonbun;
	private String roadNmBubun;
	private String aptNm;
	private int buildYear;
	private String latitude;
	private String longitude;
}
