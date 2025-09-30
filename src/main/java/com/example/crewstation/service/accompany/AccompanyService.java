package com.example.crewstation.service.accompany;


import com.example.crewstation.dto.accompany.AccompanyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccompanyService {
    List<AccompanyDTO> getAccompanies(@Param("limit") int limit);
}
