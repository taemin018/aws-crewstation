package com.example.crewstation.repository.diary;

import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.mapper.diary.DiaryMapper;
import com.example.crewstation.util.Criteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class DiaryDAO {

    private final DiaryMapper diaryMapper;

    //    다이어리 목록 조회
    public List<DiaryDTO> selectDiaryList()
    {
        return diaryMapper.selectDiaryList();
    }

    // 특정 회원이 좋아요한 일기 목록 조회
    public List<LikedDiaryDTO> findDiariesLikedByMemberId(Long memberId, Criteria criteria) {
        return diaryMapper.findDiariesLikedByMemberId(memberId, criteria);
    }

    // 특정 회원이 좋아요한 일기 수 조회
    public int countDiariesLikedByMemberId(Long memberId) {
        return diaryMapper.countDiariesLikedByMemberId(memberId);
    }

}
