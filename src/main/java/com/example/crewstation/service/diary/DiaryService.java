package com.example.crewstation.service.diary;


import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.diary.DiaryCriteriaDTO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryCriteriaDTO;
import com.example.crewstation.dto.diary.ReplyDiaryCriteriaDTO;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiaryService {

    // 특정 회원이 좋아요한 일기 목록 조회
    public LikedDiaryCriteriaDTO getDiariesLikedByMemberId(Long memberId, ScrollCriteria criteria);

    // 특정 회원이 좋아요한 일기 수 조회
    public int getCountDiariesLikedByMemberId(Long memberId);

    //  좋아요 취소
    public void cancelLike (Long memberId, Long diaryId);

    //    다이어리 목록 조회
    public List<DiaryDTO> selectDiaryList(@Param("limit") int limit);
    //    내가 댓글 쓴 일기 조회
    public ReplyDiaryCriteriaDTO getReplyDiariesByMemberId(Long memberId, ScrollCriteria criteria);

    //  내가 답글 단 일기 개수 조회
    public int getCountReplyDiariesByMemberId (Long memberId);


//    다이어리 목록들 가져오기(다이어리 서비스 쪽)
    public DiaryCriteriaDTO getDiaries(Search search, CustomUserDetails customUserDetails);

//    다이어리 이미지 개수
    public DiaryCriteriaDTO countDiaryImg(Search search, CustomUserDetails customUserDetails);
}
