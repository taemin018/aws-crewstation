package com.example.crewstation.mapper.diary;

import com.example.crewstation.domain.diary.DiaryVO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.dto.diary.ReplyDiaryDTO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;
import io.lettuce.core.Limit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiaryMapper {

    //    다이러리 목록 (메인)
    public List<DiaryDTO> selectDiaryList(@Param("limit") int limit);

    //  해당 회원이 좋아요 한 다이어리 목록(마이페이지)
    public List<LikedDiaryDTO> findDiariesLikedByMemberId(
            @Param("memberId") Long memberId,
            @Param("criteria") ScrollCriteria criteria
    );

    // 특정 회원이 좋아요한 일기 수 조회
    public int countDiariesLikedByMemberId(@Param("memberId") Long memberId);

    // 좋아요 취소
    public void deleteLike(@Param("memberId") Long memberId,
                           @Param("diaryId") Long diaryId);

    // 내가 댓글 단 다이어리 조회
    public List<ReplyDiaryDTO> selectReplyDiariesByMemberId(@Param("memberId") Long memberId,
                                                     @Param("criteria") ScrollCriteria criteria);

    // 전체 개수 (hasMore 계산용)
    public int countReplyDiariesByMemberId(@Param("memberId") Long memberId);
}
