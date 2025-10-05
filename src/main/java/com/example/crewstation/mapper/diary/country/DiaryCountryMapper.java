package com.example.crewstation.mapper.diary.country;

import com.example.crewstation.domain.diary.country.DiaryCountryVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryCountryMapper {
// 나라와 연결
    public void insert(DiaryCountryVO diaryCountryVO);
}

