package com.example.crewstation.service.crew;


import com.example.crewstation.dto.crew.CrewDTO;

import java.util.List;

public interface CrewService {
//    크루 목록
    public List<CrewDTO> getCrews(CrewDTO crewDTO);


}
