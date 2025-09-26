package com.example.crewstation.diary;

import com.example.crewstation.dto.diary.LikedDiaryDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.mapper.diary.DiaryMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    private DiaryMapper diaryMapper;

    @Test
    public void testFindLikedDiariesByMemberId() {
        Long memberId = 1L;
        Criteria criteria = new Criteria(1, 17);
        criteria.setRowCount(5);
        criteria.setOffset(0);

        List<LikedDiaryDTO> diaries = diaryMapper.findDiariesLikedByMemberId(memberId, criteria);

    }

    @Test
    public void testCountLikedDiariesByMemberId() {
        Long memberId = 1L;

        int count = diaryMapper.countDiariesLikedByMemberId(memberId);

    }

}
