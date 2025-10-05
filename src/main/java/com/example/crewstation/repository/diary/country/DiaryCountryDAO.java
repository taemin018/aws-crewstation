package com.example.crewstation.repository.diary.country;


import com.example.crewstation.domain.diary.country.DiaryCountryVO;
import com.example.crewstation.mapper.diary.country.DiaryCountryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class DiaryCountryDAO {
    private final DiaryCountryMapper diaryCountryMapper;

    // 나라와 연결
    public void save(DiaryCountryVO diaryCountryVO) {
        diaryCountryMapper.insert(diaryCountryVO);
    }
}
