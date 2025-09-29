package com.example.crewstation.controller.diary;

import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.service.diary.DiaryService;
import com.example.crewstation.util.Criteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/diaries/**")
public class DiaryRestController {

    private final DiaryService diaryService;

    // 좋아요한 일기 목록 조회
    @GetMapping("/liked/{memberId}")
    public List<LikedDiaryDTO> getLikedDiaries(@PathVariable Long memberId,
                                               @RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Criteria criteria = new Criteria(page, size);

        log.info("memberId={}, page={}, size={}", memberId, page, size);
        return diaryService.getDiariesLikedByMemberId(memberId, criteria);
    }

    // 좋아요한 일기 총 개수 반환
    @GetMapping("/liked/{memberId}/count")
    public Map<String, Integer> getLikedDiaryCount(@PathVariable Long memberId) {
        int count = diaryService.getCountDiariesLikedByMemberId(memberId);
        return Map.of("count", count);
    }
}