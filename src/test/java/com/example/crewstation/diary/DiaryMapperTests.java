package com.example.crewstation.diary;

import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.mapper.diary.DiaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DiaryMapperTests {

    @Autowired
    private DiaryMapper diaryMapper;
    @Autowired
    private DiaryDTO diaryDTO;

    @Test
    public void selectDiaryListTest() {
        diaryMapper.selectDiaryList(diaryDTO);
        diaryDTO.setPostId(1L);
        log.info("selectDiaryListTest diaryDTO={}", diaryDTO);

    }
}
