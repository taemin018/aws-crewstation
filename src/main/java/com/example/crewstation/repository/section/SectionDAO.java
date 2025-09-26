package com.example.crewstation.repository.section;

import com.example.crewstation.dto.post.section.SectionDTO;
import com.example.crewstation.mapper.section.SectionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SectionDAO {
    private final SectionMapper sectionMapper;

    //  상품글 섹션에 내용과 이미지 가져오기
    public List<SectionDTO> findSectionsByPostId(Long postId){
        return sectionMapper.selectSectionsByPostId(postId);
    }
}
