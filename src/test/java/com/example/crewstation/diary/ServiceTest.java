package com.example.crewstation.diary;

import com.example.crewstation.service.diary.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
public class ServiceTest {

    @Autowired
    private DiaryService diaryService;

    @Test
    public void testFindLikedDiaries() {
//        Long memberId = 1L;
//        ScrollCriteria criteria = new ScrollCriteria(2,18);
//        criteria.setSize(10);
//        criteria.setOffset(0);
//
//        List<LikedDiaryDTO> diaries = diaryService.getDiariesLikedByMemberId(memberId, criteria);
//        log.info("조회 결과 건수 = {}", diaries.size());
//        assertThat(diaries).isNotNull();
    }

    @Test
    public void testCountLikedDiaries() {
        Long memberId = 1L;

        int count = diaryService.getCountDiariesLikedByMemberId(memberId);
        log.info("좋아요 일기 개수 = {}", count);
        assertThat(count).isGreaterThanOrEqualTo(0);
    }
}
