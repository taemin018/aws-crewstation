package com.example.crewstation.service.accompany;


import com.example.crewstation.dto.accompany.AccompanyDTO;

import java.util.List;

public interface AccompanyService {
    List<AccompanyDTO> getAccompanies(int limit);
}
