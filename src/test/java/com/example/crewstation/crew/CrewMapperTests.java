package com.example.crewstation.crew;

import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.mapper.crew.CrewMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

// branch temp
@SpringBootTest
@Slf4j
public class CrewMapperTests {

    @Autowired
    private CrewMapper crewMapper;

    @Test
    public void getCrewById() {
        CrewDTO crewDTO = new CrewDTO();

        crewDTO.setId(1L);
        Optional<CrewDTO> result = crewMapper.getCrews(crewDTO);

        log.info("CrewDTO: {}", result);
    }

}
