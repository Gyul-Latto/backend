package com.ssafy.home.dto.apartment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ApartmentDetailDto {
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
	private String aptImg;
	private String excluUseAr;
	private String floor;
	private String description;
}
