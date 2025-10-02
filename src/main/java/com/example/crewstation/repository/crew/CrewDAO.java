package com.example.crewstation.repository.crew;

import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.mapper.crew.CrewMapper;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CrewDAO {

    private final CrewMapper crewMapper;

//    크루 목록
    public List<CrewDTO> getCrews() {

        return crewMapper.getCrews();
    }

    public int searchCountCrews(Long id) {
        return crewMapper.searchCountCrews(id);
    }

}
