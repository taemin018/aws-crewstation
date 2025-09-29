package com.example.crewstation.service.accompany;

import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.repository.accompany.AccompanyDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccompanyServiceImpl implements AccompanyService {
    private final AccompanyDAO accompanyDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "posts", key="'post_' + #id")
    public List<AccompanyDTO> getAccompanies() {
        accompanyDAO.getAccompanies();
        return accompanyDAO.getAccompanies();
    }
}
