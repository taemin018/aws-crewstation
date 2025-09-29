package com.example.crewstation.repository.file.section;

import com.example.crewstation.domain.file.section.PostSectionFileVO;
import com.example.crewstation.mapper.file.post.section.FilePostSectionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class FilePostSectionDAO {
    private final FilePostSectionMapper filePostSectionMapper;

//    값 추가하기
    public void save(PostSectionFileVO postSectionFileVO){
        filePostSectionMapper.insert(postSectionFileVO);
    }
}
