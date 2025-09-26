package com.example.crewstation.service.diary;

import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.util.Criteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryDAO diaryDAO;

    @Override
    public List<DiaryDTO> selectDiaryList(DiaryDTO diaryDTO) {
        return diaryDAO.selectDiaryList(diaryDTO);
    }

    @Override
    public List<LikedDiaryDTO> getDiariesLikedByMemberId(Long memberId, Criteria criteria) {
        log.info("memberId: {}, criteria: {}", memberId, criteria);
        return diaryDAO.findDiariesLikedByMemberId(memberId, criteria);
    }

    @Override
    public int getCountDiariesLikedByMemberId(Long memberId) {
        log.info("memberId: {}", memberId);
        return diaryDAO.countDiariesLikedByMemberId(memberId);
    }
}
