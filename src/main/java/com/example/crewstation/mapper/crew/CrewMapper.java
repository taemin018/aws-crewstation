package com.example.crewstation.mapper.crew;

import com.example.crewstation.dto.crew.CrewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CrewMapper {
//    크루 정보 조회
    public List<CrewDTO> getCrews();


}
