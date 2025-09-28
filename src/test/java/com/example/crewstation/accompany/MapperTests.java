package com.example.crewstation.accompany;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.mapper.accompany.AccompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MapperTests {
    @Autowired
    AccompanyMapper accompanyMapper;

    @Test
    public void getAccompaniesTest() {
        List<AccompanyDTO> accompanies = accompanyMapper.getAccompanies(4);

        for (AccompanyDTO accompany : accompanies) {
            log.info("accompany = {}", accompany);
        }
    }

}
