package com.ssafy.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.dto.apartment.ApartmentDealsDto;
import com.ssafy.home.repository.ApartmentDealsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentDealsService {
	private final ApartmentDealsRepository repository;

	public List<ApartmentDealsDto> getDealsByApartmentSeq(String aptSeq) {
		return repository.findDealsByApartmentSeq(aptSeq);
	}
}
