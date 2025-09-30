package com.example.crewstation.repository.crew;

import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.mapper.crew.CrewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CrewDAO {

    private final CrewMapper crewMapper;

//    크루 목록
    public List<CrewDTO> getCrews(CrewDTO crewDTO) {

        return crewMapper.getCrews(crewDTO);
    }

}
