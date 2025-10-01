package com.example.crewstation.diary;

import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.mapper.diary.DiaryMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    private DiaryMapper diaryMapper;

    @Test
    public void testFindLikedDiariesByMemberId() {
        Long memberId = 1L;
        ScrollCriteria criteria = new ScrollCriteria(1, 17);
        criteria.setSize(5);
        criteria.setOffset(0);

        List<LikedDiaryDTO> diaries = diaryMapper.findDiariesLikedByMemberId(memberId, criteria);

    }

    @Test
    public void testCountLikedDiariesByMemberId() {
        Long memberId = 1L;

        int count = diaryMapper.countDiariesLikedByMemberId(memberId);

    }


    @Test
    public void testSelectAllByKeyword(){
        Search search = new Search();
        search.setKeyword("");
        search.setCategory("null");
        Criteria criteria = new Criteria(1,2);

        String order = "diary_like_count";
        List<DiaryDTO> diaryDTOS = diaryMapper.selectAllByKeyword(criteria, search);
        diaryDTOS.stream().map(DiaryDTO::toString).forEach(log::info);
    }

    @Test
    public void testSelectCountAllByKeyword(){
        Search search = new Search();
        search.setKeyword("");
        search.setCategory("null");

        String order = "diary_like_count";
        int count = diaryMapper.selectCountAllByKeyword(search);
        log.info("{}",count);
    }

    @Test
    @Transactional
    public void testUpdateLikeCount(){
        diaryMapper.updateLikeCount(+1,30L);
    }
}
