package com.example.crewstation.repository.accompany;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.mapper.accompany.AccompanyMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccompanyDAO {
    private final AccompanyMapper accompanyMapper;

    public List<AccompanyDTO> getAccompanies(@Param("limit") int limit) {
        return accompanyMapper.getAccompanies(limit);
    }
}
