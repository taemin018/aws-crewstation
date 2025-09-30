package com.example.crewstation.diary;

import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.util.ScrollCriteria;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private DiaryDAO diaryDAO;

    @Test
    public void testFindLikedDiaries() {
        Long memberId = 1L;
        ScrollCriteria criteria = new ScrollCriteria(1,17);
        criteria.setSize(3);
        criteria.setOffset(0);

        List<LikedDiaryDTO> diaries = diaryDAO.findDiariesLikedByMemberId(memberId, criteria);

        log.info("FindLikedDiaries size = {}", diaries.size());
        diaries.forEach(diary -> log.info("Diary: {}", diary));
    }

    @Test
    public void testCountLikedDiaries() {
        Long memberId = 1L;
        int count = diaryDAO.countDiariesLikedByMemberId(memberId);
    }
}
