package com.example.crewstation.repository.file.section;

import com.example.crewstation.domain.file.section.PostSectionFileVO;
import com.example.crewstation.mapper.file.post.section.FilePostSectionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.OptionalLong;

@RequiredArgsConstructor
@Repository
@Slf4j
public class FilePostSectionDAO {
    private final FilePostSectionMapper filePostSectionMapper;

//    값 추가하기
    public void save(PostSectionFileVO postSectionFileVO){
        filePostSectionMapper.insert(postSectionFileVO);
    }

    //   hard delete
    public void delete(Long sectionId){
        filePostSectionMapper.delete(sectionId);
    }

    //  섹션 아이디로 파일 아이디 찾기
    public Optional<Long> findPostSectionFileIdBySectionId(Long sectionId){
        return filePostSectionMapper.selectPostSectionFileIdBySectionId(sectionId);
    }
}
