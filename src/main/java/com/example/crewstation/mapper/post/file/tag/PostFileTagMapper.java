package com.example.crewstation.mapper.post.file.tag;

import com.example.crewstation.domain.post.file.tag.PostFileTagVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostFileTagMapper {

//    태그 저장
    public void insert(PostFileTagVO postFileTagVO);
}
