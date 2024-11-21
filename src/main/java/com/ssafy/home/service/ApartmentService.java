package com.ssafy.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.dto.apartment.ApartmentDto;
import com.ssafy.home.dto.apartment.DongCodeDto;
import com.ssafy.home.repository.ApartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApartmentService {

	private final ApartmentRepository apartmentRepository;

	public List<ApartmentDto> searchApartments(String sido, String gugun, String dong) {
		// 시/구/동 정보로 동 코드 조회
		DongCodeDto dongCodeDto = apartmentRepository.findDongCode(sido, gugun, dong);

		if (dongCodeDto == null) {
			throw new IllegalArgumentException("Invalid location information provided.");
		}

		// 동 코드로 아파트 리스트 조회
		return apartmentRepository.findApartmentsByDongCode(dongCodeDto.getDongCode());
	}
}
