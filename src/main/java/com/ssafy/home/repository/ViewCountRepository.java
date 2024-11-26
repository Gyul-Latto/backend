package com.ssafy.home.repository;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.dto.views.DailyViewsDto;

@Mapper
public interface ViewCountRepository {
	void saveViewCount(DailyViewsDto dailyViewsDto);
}
