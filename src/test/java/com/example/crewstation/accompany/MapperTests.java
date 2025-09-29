package com.example.crewstation.accompany;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.mapper.accompany.AccompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MapperTests {
    @Autowired
    AccompanyMapper accompanyMapper;
    @Autowired
    private AccompanyDTO accompanyDTO;

    @Test
    public void getAccompaniesTest() {
        accompanyMapper.getAccompanies();
        accompanyDTO.setPostId(1L);
        log.info("accompanyDTO={}", accompanyDTO);

        }
    }


