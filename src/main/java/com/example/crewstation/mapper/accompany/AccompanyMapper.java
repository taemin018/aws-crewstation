package com.example.crewstation.mapper.accompany;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccompanyMapper {
//    동행 리스트 목록 조회
    public List<AccompanyDTO> getAccompanies(@Param("limit") int limit);


}
