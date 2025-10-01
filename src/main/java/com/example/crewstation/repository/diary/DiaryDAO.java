package com.example.crewstation.repository.diary;

import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.dto.diary.ReplyDiaryDTO;
import com.example.crewstation.mapper.diary.DiaryMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class DiaryDAO {

    private final DiaryMapper diaryMapper;

    //    다이어리 목록 조회
    public List<DiaryDTO> selectDiaryList(@Param("limit") int limit)
    {
        return diaryMapper.selectDiaryList(limit);
    }

    // 특정 회원이 좋아요한 일기 목록 조회
    public List<LikedDiaryDTO> findDiariesLikedByMemberId(Long memberId, ScrollCriteria criteria) {
        return diaryMapper.findDiariesLikedByMemberId(memberId, criteria);
    }

    // 특정 회원이 좋아요한 일기 수 조회
    public int countDiariesLikedByMemberId(Long memberId) {
        return diaryMapper.countDiariesLikedByMemberId(memberId);
    }

    //    좋아요 취소
    public void deleteLike(Long memberId, Long diaryId) {
        diaryMapper.deleteLike(memberId, diaryId);
    }

    // 내가 댓글 단 일기 조회
    public List<ReplyDiaryDTO> findReplyDiariesByMemberId(Long memberId, ScrollCriteria criteria) {
        return diaryMapper.selectReplyDiariesByMemberId(memberId, criteria);
    }

    //  내가 댓글 단 일기 총 개수
    public int countReplyDiariesByMemberId(Long memberId) {
        return diaryMapper.countReplyDiariesByMemberId(memberId);
    }

    //  다이어리 목록(다이어리 서비스쪽)
    public List<DiaryDTO> findAllByKeyword(@Param("criteria") Criteria criteria, @Param("search") Search search){
        return diaryMapper.selectAllByKeyword(criteria, search);
    }
    // 다이리 목록 개수(다이어리 서비스쪽)
    public int findCountAllByKeyword(@Param("search") Search search){
        return diaryMapper.selectCountAllByKeyword(search);
    }

    public void changeLikeCount(int diff,Long postId){
        diaryMapper.updateLikeCount(diff,postId);
    }
}
