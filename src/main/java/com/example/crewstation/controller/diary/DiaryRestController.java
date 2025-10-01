package com.example.crewstation.controller.diary;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.diary.*;
import com.example.crewstation.service.diary.DiaryService;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/diaries/**")
public class DiaryRestController {

    private final DiaryService diaryService;

    // 좋아요한 일기 목록 조회 (무한스크롤)
    @GetMapping("/liked/{memberId}")
    public ResponseEntity<LikedDiaryCriteriaDTO> getLikedDiaries(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        ScrollCriteria criteria = new ScrollCriteria(page, size);
        LikedDiaryCriteriaDTO dto = diaryService.getDiariesLikedByMemberId(memberId, criteria);

        return ResponseEntity.ok(dto);
    }

    // 좋아요한 일기 총 개수 반환
    @GetMapping("/liked/{memberId}/count")
    public ResponseEntity<Integer> getLikedDiaryCount(@PathVariable Long memberId) {
        int count = diaryService.getCountDiariesLikedByMemberId(memberId);
        return ResponseEntity.ok(count);
    }

    // 좋아요 취소
    @DeleteMapping("/liked/{memberId}/{diaryId}")
    public ResponseEntity<Map<String, Object>> cancelLikedDiary(
            @PathVariable Long memberId,
            @PathVariable Long diaryId) {
        try {
            diaryService.cancelLike(memberId, diaryId);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            log.error("좋아요 취소 실패 - memberId={}, diaryId={}", memberId, diaryId, e);
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // 내가 댓글 단 다이어리 목록 (무한스크롤)
    @GetMapping("/replies/{memberId}")
    public ResponseEntity<ReplyDiaryCriteriaDTO> getReplyDiaries(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        ScrollCriteria criteria = new ScrollCriteria(page, size);
        ReplyDiaryCriteriaDTO dto = diaryService.getReplyDiariesByMemberId(memberId, criteria);

        return ResponseEntity.ok(dto);
    }

    // 댓글 단 다이어리 개수 조회
    @GetMapping("/replies/{memberId}/count")
    public ResponseEntity<Integer> getReplyDiaryCount(@PathVariable Long memberId) {
        int count = diaryService.getCountReplyDiariesByMemberId(memberId);
        return ResponseEntity.ok(count);
    }

    @GetMapping
    public ResponseEntity<DiaryCriteriaDTO> getDiaries(Search search,
                                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("search: {}", search);
        DiaryCriteriaDTO diaries = diaryService.getDiaries(search, customUserDetails);
        log.info("diaries::::::::::::::::::::::::::::::::::::: {}", diaries);
        return ResponseEntity.ok(diaries);
    }
}