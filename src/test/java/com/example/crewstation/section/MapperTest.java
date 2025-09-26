package com.example.crewstation.section;

import com.example.crewstation.mapper.section.SectionMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    SectionMapper sectionMapper;

    @Test
    public void testSelectSectionsByPostId(){
        log.info("selectSectionsByPostId {}", sectionMapper.selectSectionsByPostId(3L));
    }
}
