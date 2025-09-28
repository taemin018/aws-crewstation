package com.example.crewstation.service.accompany;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.repository.accompany.AccompanyDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccompanyServiceImpl implements AccompanyService {
    private final AccompanyDAO accompanyDAO;

    @Override
    public List<AccompanyDTO> getAccompanies(int limit) {
        return accompanyDAO.getAccompanies(limit);
    }
}
