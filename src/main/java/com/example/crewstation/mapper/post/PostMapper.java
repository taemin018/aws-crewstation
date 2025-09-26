package com.example.crewstation.mapper.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
//    게시글 신고 추가해주기
    public void insertPostReport(@Param("reportId")Long reportId ,@Param("postId") Long postId);
}
