package com.example.crewstation.mapper.file.post.section;

import com.example.crewstation.domain.file.section.PostSectionFileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface FilePostSectionMapper {
//   값 추가하기
    public void insert(PostSectionFileVO sectionFileVO);

//   hard delete
    public void delete(Long sectionId);

//  섹션 아이디로 파일 아이디 찾기
    public Optional<Long> selectPostSectionFileIdBySectionId(Long sectionId);
}
