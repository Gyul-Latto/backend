package com.ssafy.home.repository;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.dto.views.ViewsDto;

@Mapper
public interface ViewCountRepository {
	void saveViewCount(ViewsDto viewsDto);

	void saveDailyViews(ViewsDto viewsDto);
}
