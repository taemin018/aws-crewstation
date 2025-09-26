package com.example.crewstation.repository.diary;

import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.mapper.diary.DiaryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiaryDAO {

    private final DiaryMapper diaryMapper;

//    다이어리 목록 조회
    public List<DiaryDTO> selectDiaryList(DiaryDTO diaryDTO) {
        return diaryMapper.selectDiaryList(diaryDTO);
    }
}
