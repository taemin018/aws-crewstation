package com.example.crewstation.crew;

import com.example.crewstation.domain.crew.CrewVO;
import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.mapper.crew.CrewMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class CrewMapperTests {

    @Autowired
    private CrewMapper crewMapper;

    @Autowired
    private CrewDTO crewDTO;

    @Test
    public void testGetCrews() {
        crewMapper.getCrews(crewDTO);
        crewDTO.setId(1L);
        log.info("getCrewById");



    }

}
