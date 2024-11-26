package com.ssafy.home.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.dto.apartment.ApartmentDealsDto;
import com.ssafy.home.service.ApartmentDealsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/apt/search")
@RequiredArgsConstructor
public class ApartmentDealsController {
	private final ApartmentDealsService service;

	@GetMapping("/deals")
	public List<ApartmentDealsDto> getDeals(@RequestParam String aptSeq) {
		return service.getDealsByApartmentSeq(aptSeq);
	}
}
