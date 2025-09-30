package com.example.crewstation.mapper.file.post.section;

import com.example.crewstation.domain.file.section.PostSectionFileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FilePostSectionMapper {
//   값 추가하기
    public void insert(PostSectionFileVO sectionFileVO);
}
