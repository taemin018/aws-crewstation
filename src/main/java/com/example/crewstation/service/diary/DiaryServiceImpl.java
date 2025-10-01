package com.example.crewstation.service.diary;

import com.example.crewstation.dto.diary.*;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.DateUtils;
import com.example.crewstation.util.ScrollCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryDAO diaryDAO;
    private final S3Service s3Service;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DiaryDTO> selectDiaryList(int limit) {
        List<DiaryDTO> diaries = diaryDAO.selectDiaryList(limit);
        diaries.forEach(diary -> {
            log.info("Selected diary: {}", diary);
            diary.setFilePath(s3Service.getPreSignedUrl(diary.getFilePath(),
                    Duration.ofMinutes(5)));
        });
        return diaries;
    }

    @Override
    public LikedDiaryCriteriaDTO getDiariesLikedByMemberId(Long memberId, ScrollCriteria criteria) {
        log.info("좋아요 다이어리 조회 - memberId={}, page={}, size={}",
                memberId, criteria.getPage(), criteria.getSize());

        // 목록 조회
        List<LikedDiaryDTO> diaries = diaryDAO.findDiariesLikedByMemberId(memberId, criteria);

        // 전체 개수 (hasMore 계산용)
        int totalCount = diaryDAO.countDiariesLikedByMemberId(memberId);
        criteria.setTotal(totalCount);

        // DTO 조립
        LikedDiaryCriteriaDTO dto = new LikedDiaryCriteriaDTO();
        dto.setLikedDiaryDTOs(diaries);
        dto.setCriteria(criteria);

        return dto;
    }

    //  좋아요 한 다이어리 개수
    @Override
    public int getCountDiariesLikedByMemberId(Long memberId) {
        log.info("memberId: {}", memberId);
        return diaryDAO.countDiariesLikedByMemberId(memberId);
    }

    //  좋아요 취소
    @Override
    public void cancelLike(Long memberId, Long diaryId) {
        diaryDAO.deleteLike(memberId, diaryId);
    }

    // 댓글 단 다이어리 목록 조회
    @Override
    public ReplyDiaryCriteriaDTO getReplyDiariesByMemberId(Long memberId, ScrollCriteria criteria) {
        log.info("댓글 단 다이어리 조회 - memberId={}, page={}, size={}",
                memberId, criteria.getPage(), criteria.getSize());

        List<ReplyDiaryDTO> diaries = diaryDAO.findReplyDiariesByMemberId(memberId, criteria);

        // 상대시간 변환
        diaries.forEach(diary ->
                diary.setRelativeDatetime(DateUtils.toRelativeTime(diary.getCreatedDatetime()))
        );

        // 전체 개수 (hasMore 계산용)
        int totalCount = diaryDAO.countReplyDiariesByMemberId(memberId);
        criteria.setTotal(totalCount);

        // DTO 조립
        ReplyDiaryCriteriaDTO dto = new ReplyDiaryCriteriaDTO();
        dto.setReplyDiaryDTOs(diaries);
        dto.setCriteria(criteria);

        return dto;
    }

    //  내가 댓글 단 일기 개수
    @Override
    public int getCountReplyDiariesByMemberId(Long memberId) {
        log.info("memberId: {}", memberId);
        return diaryDAO.countReplyDiariesByMemberId(memberId);
    }
}
