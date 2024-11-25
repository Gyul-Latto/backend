package com.ssafy.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.service.ViewCountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/views")
@RequiredArgsConstructor
@Slf4j
public class ViewCountController {
	private final ViewCountService viewCountService;

	@GetMapping("/{aptSeq}")
	public void updateViewCount(@PathVariable String aptSeq) {
		log.info("updateViewCount: aptSeq={}", aptSeq);
		viewCountService.updateHourlyViewCount(aptSeq,"00-01");
		log.info("updateViewCount: aptSeq={}", aptSeq);
	}

}
