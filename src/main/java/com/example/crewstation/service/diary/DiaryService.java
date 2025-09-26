package com.example.crewstation.service.diary;

import com.example.crewstation.dto.diary.DiaryDTO;

import java.util.List;

public interface DiaryService {

//    다이어리 목록 조회
    public List<DiaryDTO> selectDiaryList(DiaryDTO diaryDTO);
}
