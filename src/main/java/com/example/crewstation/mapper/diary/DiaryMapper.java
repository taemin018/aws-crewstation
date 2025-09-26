package com.example.crewstation.mapper.diary;

import com.example.crewstation.domain.diary.DiaryVO;
import com.example.crewstation.dto.diary.DiaryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

//    다이러리 목록 (메인)
    public List<DiaryDTO> selectDiaryList(DiaryDTO diaryDTO);
}
