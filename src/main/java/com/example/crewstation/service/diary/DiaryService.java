package com.example.crewstation.service.diary;


import com.example.crewstation.dto.diary.*;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;

import java.util.List;

public interface DiaryService {

    // 특정 회원이 좋아요한 일기 목록 조회
    public LikedDiaryCriteriaDTO getDiariesLikedByMemberId(Long memberId, ScrollCriteria criteria);

    // 특정 회원이 좋아요한 일기 수 조회
    public int getCountDiariesLikedByMemberId(Long memberId);

    //    다이어리 목록 조회
    public List<DiaryDTO> selectDiaryList(DiaryDTO diaryDTO);
    //    내가 댓글 쓴 일기 조회
    public ReplyDiaryCriteriaDTO getReplyDiariesByMemberId(Long memberId, ScrollCriteria criteria);

    //  내가 답글 단 일기 개수 조회
    public int getCountReplyDiariesByMemberId (Long memberId);

}
