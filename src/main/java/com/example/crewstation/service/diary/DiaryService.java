package com.example.crewstation.service.diary;


import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.util.Criteria;

import java.util.List;

public interface DiaryService {

    // 특정 회원이 좋아요한 일기 목록 조회
    List<LikedDiaryDTO> getDiariesLikedByMemberId(Long memberId, Criteria criteria);

    // 특정 회원이 좋아요한 일기 수 조회
    int getCountDiariesLikedByMemberId(Long memberId);

    //    다이어리 목록 조회
    public List<DiaryDTO> selectDiaryList(DiaryDTO diaryDTO);

}
