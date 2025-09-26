package com.example.crewstation.service.diary;

import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.repository.diary.DiaryDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryServiceImpl implements DiaryService {


    private final DiaryDAO diaryDAO;

    @Override
    public List<DiaryDTO> selectDiaryList(DiaryDTO diaryDTO) {
        return diaryDAO.selectDiaryList(diaryDTO);
    }
}
