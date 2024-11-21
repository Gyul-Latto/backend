package com.ssafy.home.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.SuccessResponse;
import com.ssafy.home.dto.apartment.ApartmentDto;
import com.ssafy.home.dto.apartment.DongCodeDto;
import com.ssafy.home.service.ApartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ApartmentController {

	private final ApartmentService apartmentService;

	@PostMapping("/apt/search")
	public SuccessResponse<List<ApartmentDto>> searchApartments(@RequestBody DongCodeDto dongCodeDto) {
		System.out.println("Received DongCodeDto: " + dongCodeDto);
		List<ApartmentDto> apartments = apartmentService.searchApartments(
			dongCodeDto.getSidoName(),
			dongCodeDto.getGugunName(),
			dongCodeDto.getDongName()
		);
		return SuccessResponse.of(apartments);
	}

}
